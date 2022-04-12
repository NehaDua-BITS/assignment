package com.intuit.profilevalidationsystem.helper;

import com.intuit.profilevalidationsystem.constants.SubscriptionFrequency;
import com.intuit.profilevalidationsystem.exceptions.NotSupportedException;
import com.intuit.profilevalidationsystem.exceptions.constants.ErrorCodes;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;

public class SubscriptionHelper {

    public static Date calculateEndDate(Date startDate, SubscriptionFrequency frequency) {
        LocalDate localDate = startDate.toLocalDate();
        Month month = localDate.getMonth();
        int year = localDate.getYear();
        switch (frequency) {
            case MONTHLY:
                month = month.plus(1);
                break;

            case QUATERLY:
                month = month.plus(3);
                break;

            case YEARLY:
                year += 1;
                break;

            default:
                throw new NotSupportedException(ErrorCodes.INVALID_SUBSCRIPTION_FREQUENCY, frequency.toString());
        }

        //todo: check if date is valid
        LocalDate localEndDate = LocalDate.of(year, month, localDate.getDayOfMonth());
        return Date.valueOf(localEndDate);
    }

}
