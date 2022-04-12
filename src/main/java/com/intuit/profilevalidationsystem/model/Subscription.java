package com.intuit.profilevalidationsystem.model;

import com.intuit.profilevalidationsystem.constants.ProductType;
import com.intuit.profilevalidationsystem.constants.Status;
import com.intuit.profilevalidationsystem.constants.SubscriptionFrequency;
import com.intuit.profilevalidationsystem.helper.SubscriptionHelper;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @Column(name = "subscription_id")
    @Type(type = "uuid-char")
    private UUID subscriptionId;

    @Column(name = "profile_id")
    @Type(type = "uuid-char")
    private UUID profileId;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type")
    private ProductType productType;

    @Enumerated(EnumType.STRING)
    private SubscriptionFrequency frequency;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Subscription(UUID profileId, ProductType productType, SubscriptionFrequency frequency) {
        this.subscriptionId = UUID.randomUUID();
        this.profileId = profileId;
        this.startDate = new Date(System.currentTimeMillis());
        this.endDate = SubscriptionHelper.calculateEndDate(startDate, frequency);
        this.productType = productType;
        this.frequency = frequency;
        this.status = Status.ACTIVE;
    }

    public void cancelSubscription() {
        this.status = Status.CANCELLED;
    }

    public void expireSubscription() {
        this.status = Status.EXPIRED;
    }
}
