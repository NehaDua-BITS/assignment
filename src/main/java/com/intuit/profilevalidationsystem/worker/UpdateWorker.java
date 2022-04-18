package com.intuit.profilevalidationsystem.worker;

import com.intuit.profilevalidationsystem.constants.UpdateStatus;
import com.intuit.profilevalidationsystem.dao.UpdateTransactionRepository;
import com.intuit.profilevalidationsystem.dao.ValidationTransactionRepository;
import com.intuit.profilevalidationsystem.dto.ProfileDTO;
import com.intuit.profilevalidationsystem.helper.Mapper;
import com.intuit.profilevalidationsystem.kafka.UpdateEvent;
import com.intuit.profilevalidationsystem.model.Profile;
import com.intuit.profilevalidationsystem.model.UpdateTransaction;
import com.intuit.profilevalidationsystem.service.impl.ProfileServiceImpl;
import com.intuit.profilevalidationsystem.service.impl.ValidationTransactionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
public class UpdateWorker {

    @Autowired
    private ValidationTransactionServiceImpl validationTransactionService;

    @Autowired
    private UpdateTransactionRepository updateTransactionRepository;

    @Autowired
    private ValidationTransactionRepository validationTransactionRepository;

    @Autowired
    private ProfileServiceImpl profileService;

    public void updateOverallValidationStatus(UpdateEvent event, UpdateStatus[] updateStatuses, ProfileDTO profileDTO) {
        log.info("Update validation status check worker started !!");

        //iterate over map to find completed txs
        UpdateStatus overallStatus = getOverallStatus(updateStatuses);

        UUID txId = UUID.fromString(event.getRequestId());
        log.info("Updating overall transaction status: userId={} ; TxId = {} ; status = {}", event.getUserId(), txId, overallStatus);
        UpdateTransaction updateTransaction = validationTransactionService.updateTransactionEntry(UUID.fromString(event.getRequestId()), overallStatus);

//        UpdateTransaction updateTransaction = updateTransactionRepository.findById(txId).get();
        if (overallStatus == UpdateStatus.ACCEPTED) {
                log.info("All products accepted. Updating data for transaction id = {}", event.getRequestId());
                try {
                    Profile profile = profileService.updateProfile(UUID.fromString(event.getUserId()), profileDTO);
                    log.info("Profile Updated for update tx id : {}; date : {}", txId, profile);
                    updateTransaction.setStatus(UpdateStatus.COMPLETED);
                    updateTransaction.setLastUpdatedTimestamp(Date.from(Instant.now()));
                    updateTransactionRepository.save(updateTransaction);
                    log.info("Update transaction completed : update tx id = {}; profile id = {}",
                            updateTransaction.getUpdateTransactionId(), updateTransaction.getProfileId());
                } catch (Exception ex) {
                    log.error("Update failed for update tx id : {}, ex: {}", txId, ex.getMessage());
                }
        }
        else if (overallStatus != UpdateStatus.IN_PROGRESS) { //rejected, expired or timeout
            //deleteValidationTransactions(transactions, entry.getKey());
            updateTransaction.setStatus(UpdateStatus.FAILED);
            updateTransaction.setLastUpdatedTimestamp(Date.from(Instant.now()));
            updateTransactionRepository.save(updateTransaction);
            log.info("Update transaction failed : update tx id = {}; profile id = {}",
                    updateTransaction.getUpdateTransactionId(), updateTransaction.getProfileId());
        }
    }

    private UpdateStatus getOverallStatus(UpdateStatus[] statusList) {
        int count = statusList.length;
        long acceptCount = Arrays.stream(statusList).filter(i -> i == UpdateStatus.ACCEPTED).count();

        if (count == acceptCount) {
            return UpdateStatus.ACCEPTED;
        }

        long rejectCount = Arrays.stream(statusList).filter(i -> i != UpdateStatus.ACCEPTED && i != UpdateStatus.IN_PROGRESS).count();
        if (rejectCount > 0) {
            return UpdateStatus.REJECTED;
        }
        return UpdateStatus.IN_PROGRESS;
    }

}
