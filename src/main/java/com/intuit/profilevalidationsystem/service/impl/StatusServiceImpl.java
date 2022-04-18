package com.intuit.profilevalidationsystem.service.impl;

import com.intuit.profilevalidationsystem.dao.UpdateTransactionRepository;
import com.intuit.profilevalidationsystem.exceptions.types.InvalidInputException;
import com.intuit.profilevalidationsystem.exceptions.constants.ErrorCodes;
import com.intuit.profilevalidationsystem.model.UpdateTransaction;
import com.intuit.profilevalidationsystem.service.StatusService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Service
public class StatusServiceImpl implements StatusService {

    private UpdateTransactionRepository updateTransactionRepository;

    @Autowired
    public StatusServiceImpl(UpdateTransactionRepository updateTransactionRepository) {
        this.updateTransactionRepository = updateTransactionRepository;
    }

    @Override
    public UpdateTransaction getUpdateTransaction(UUID transactionId) {
        Optional<UpdateTransaction> optionalUpdateTransaction = updateTransactionRepository.findById(transactionId);
        if (!optionalUpdateTransaction.isPresent()) {
            throw new InvalidInputException(ErrorCodes.NO_REQUEST_FOUND);
        }
        return optionalUpdateTransaction.get();
    }
}
