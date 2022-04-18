package com.intuit.profilevalidationsystem.dto.response;

import com.intuit.profilevalidationsystem.constants.UpdateStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusDTO {

    private UUID updateRequestId;

    private UpdateStatus updateStatus;

    private Date lastUpdatedTime;

}
