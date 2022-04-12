package com.intuit.profilevalidationsystem.model;

import com.intuit.profilevalidationsystem.constants.ValidationStatus;
import com.intuit.profilevalidationsystem.kafka.UpdateEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "update_transactions")
public class UpdateTransaction {

    @Id
    @Column(name = "update_tx_id")
    private UUID updateTransactionId;

    @Column(name = "profile_id")
    private UUID profileId;

    @Column(name = "status")
    private ValidationStatus status;

    @Column(name = "create_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTimestamp;

    @Lob
    private UpdateEvent event;

}
