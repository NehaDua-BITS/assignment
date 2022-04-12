package com.intuit.profilevalidationsystem.model;

import com.intuit.profilevalidationsystem.constants.ProductType;
import com.intuit.profilevalidationsystem.constants.Status;
import com.intuit.profilevalidationsystem.constants.SubscriptionFrequency;
import com.intuit.profilevalidationsystem.helper.SubscriptionHelper;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    private UUID subscriptionId;

    private UUID profileId;

    private Date startDate;

    private Date endDate;

    @Enumerated(EnumType.STRING)
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
