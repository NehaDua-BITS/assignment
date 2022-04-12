package com.intuit.profilevalidationsystem.dao;

import com.intuit.profilevalidationsystem.constants.ProductType;
import com.intuit.profilevalidationsystem.model.UpdateValidationTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ValidationTransactionRepository extends JpaRepository<UpdateValidationTransaction, UUID> {

    UpdateValidationTransaction findByUpdateTransactionIdAndProduct(UUID updateTransactionId, ProductType product);
}
