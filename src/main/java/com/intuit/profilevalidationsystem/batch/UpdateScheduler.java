package com.intuit.profilevalidationsystem.batch;

import com.intuit.profilevalidationsystem.constants.ValidationStatus;
import com.intuit.profilevalidationsystem.controller.ProfileController;
import com.intuit.profilevalidationsystem.dao.UpdateTransactionRepository;
import com.intuit.profilevalidationsystem.dao.ValidationTransactionRepository;
import com.intuit.profilevalidationsystem.model.UpdateValidationTransaction;
import com.intuit.profilevalidationsystem.service.impl.ValidationTransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class UpdateScheduler {

    @Autowired
    private ValidationTransactionServiceImpl validationTransactionService;

    @Autowired
    private UpdateTransactionRepository updateTransactionRepository;

    @Autowired
    private ValidationTransactionRepository validationTransactionRepository;

    @Autowired
    private ProfileController profileController;

    public void checkValidationResponses() {
        List<UpdateValidationTransaction> transactions = validationTransactionRepository.findAll();
        Map<UUID, ValidationStatus> statusMapByUUID = new HashMap<>();

        for (UpdateValidationTransaction tx : transactions) {
            ValidationStatus status = statusMapByUUID.get(tx.getUpdateTransactionId());
            if (status == null || tx.getStatus() != ValidationStatus.ACCEPTED) {
                statusMapByUUID.put(tx.getUpdateTransactionId(), tx.getStatus());
            }
        }

        //iterate over map to find completed txs
        for (Map.Entry<UUID, ValidationStatus> entry : statusMapByUUID.entrySet()) {
            if (entry.getValue() == ValidationStatus.ACCEPTED) {
                validationTransactionService.updateTransactionEntry(entry.getKey(), entry.getValue());
                profileController.updateProfile(entry.getKey(),
                        updateTransactionRepository.findById(entry.getKey()).get().getEvent().getProfileDTO());
            }
        }

    }

}
