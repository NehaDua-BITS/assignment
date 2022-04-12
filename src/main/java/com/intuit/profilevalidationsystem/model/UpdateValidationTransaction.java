package com.intuit.profilevalidationsystem.model;

import com.intuit.profilevalidationsystem.constants.ProductType;
import com.intuit.profilevalidationsystem.constants.ValidationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "update_validation_transactions")
public class UpdateValidationTransaction {

    @Column(name = "update_tx_id")
    private UUID updateTransactionId;

    @Id
    @Column(name = "sub_tx_id")
    private UUID subTransactionId;

    @Enumerated(EnumType.STRING)
    private ProductType product;

    @Enumerated(EnumType.STRING)
    private ValidationStatus status;

    @Column(name = "request_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestTimestamp;

    @Column(name = "response_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date responseTimestamp;

}
