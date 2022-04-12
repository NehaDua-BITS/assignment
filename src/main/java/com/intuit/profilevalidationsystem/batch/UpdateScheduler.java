package com.intuit.profilevalidationsystem.batch;

import com.intuit.profilevalidationsystem.constants.ValidationStatus;
import com.intuit.profilevalidationsystem.controller.ProfileController;
import com.intuit.profilevalidationsystem.dao.UpdateTransactionRepository;
import com.intuit.profilevalidationsystem.dao.ValidationTransactionRepository;
import com.intuit.profilevalidationsystem.model.UpdateValidationTransaction;
import com.intuit.profilevalidationsystem.service.impl.ValidationTransactionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@EnableScheduling
public class UpdateScheduler {

    @Autowired
    private ValidationTransactionServiceImpl validationTransactionService;

    @Autowired
    private UpdateTransactionRepository updateTransactionRepository;

    @Autowired
    private ValidationTransactionRepository validationTransactionRepository;

    @Autowired
    private ProfileController profileController;

    /**
     * Method scheduled to check validation responses in DB every 2 minutes
     * If all are accepted, update will be performed
     * If any validation is rejected, timed out or expired, update will be rejected
     * Also, these transactions are removed from validation transaction table.
     */
    @Scheduled(initialDelay = 1000, fixedRate = 120000)
    public void checkValidationResponses() {
        log.info("Update validation status check scheduler started !!");
        List<UpdateValidationTransaction> transactions = validationTransactionRepository.findAll();
        Map<UUID, List<ValidationStatus>> statusMapByUUID = new HashMap<>();

        for (UpdateValidationTransaction tx : transactions) {
           List<ValidationStatus> statusList = statusMapByUUID.get(tx.getUpdateTransactionId());
            if (statusList == null) {
                statusList = new ArrayList<>();
                statusMapByUUID.put(tx.getUpdateTransactionId(), statusList);
            }
            statusList.add(tx.getStatus());
        }

        //iterate over map to find completed txs
        for (Map.Entry<UUID, List<ValidationStatus>> entry : statusMapByUUID.entrySet()) {

            ValidationStatus overallStatus = getOverallStatus(entry.getValue());

            log.info("Updating overall transaction status id = {} ; status = {}", entry.getKey(), overallStatus);
            validationTransactionService.updateTransactionEntry(entry.getKey(), overallStatus);

            if (overallStatus == ValidationStatus.ACCEPTED) {
                log.info("All products accepted. Updating data for transaction id = {}", entry.getKey());
                profileController.updateProfile(entry.getKey(),
                        updateTransactionRepository.findById(entry.getKey()).get().getEvent().getProfileDTO());
            }

            if (overallStatus != ValidationStatus.IN_PROGRESS) {
                deleteValidationTransactions(transactions, entry.getKey());
            }
        }

    }

    private ValidationStatus getOverallStatus(List<ValidationStatus> statusList) {
        int count = statusList.size();
        long acceptCount = statusList.stream().filter(i -> i == ValidationStatus.ACCEPTED).count();

        if (count == acceptCount) {
            return ValidationStatus.ACCEPTED;
        }

        long rejectCount = statusList.stream().filter(i -> i != ValidationStatus.ACCEPTED && i != ValidationStatus.IN_PROGRESS).count();
        if (rejectCount > 0) {
            return ValidationStatus.REJECTED;
        }
        return ValidationStatus.IN_PROGRESS;
    }

    private void deleteValidationTransactions(List<UpdateValidationTransaction> transactions, UUID updateId) {
        log.info("Deleting temporary validation transactions for update id : {}", updateId);
        List<UpdateValidationTransaction> list = transactions.stream().filter(t -> t.getUpdateTransactionId().equals(updateId)).collect(Collectors.toList());
        validationTransactionRepository.deleteInBatch(list);
    }

}
