package com.intuit.profilevalidationsystem.model;

import com.intuit.profilevalidationsystem.constants.ProductType;
import com.intuit.profilevalidationsystem.constants.UpdateStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

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
    @Type(type = "uuid-char")
    private UUID updateTransactionId;

    @Id
    @Column(name = "sub_tx_id")
    @Type(type = "uuid-char")
    private UUID subTransactionId;

    @Enumerated(EnumType.STRING)
    private ProductType product;

    @Enumerated(EnumType.STRING)
    private UpdateStatus status;

    @Column(name = "create_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTimestamp;

    @Column(name = "last_updated_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedTimestamp;

}
