package com.intuit.profilevalidationsystem.model;

import com.intuit.profilevalidationsystem.constants.UpdateStatus;
import com.intuit.profilevalidationsystem.kafka.UpdateEvent;
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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "update_transactions")
public class UpdateTransaction {

    @Id
    @Column(name = "update_tx_id")
    @Type(type = "uuid-char")
    private UUID updateTransactionId;

    @Column(name = "profile_id")
    @Type(type = "uuid-char")
    private UUID profileId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UpdateStatus status;

    @Column(name = "request_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestTimestamp;
    
    @Column(name = "create_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTimestamp;

    @Column(name = "last_updated_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedTimestamp;

}
