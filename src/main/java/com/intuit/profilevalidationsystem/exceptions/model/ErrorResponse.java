package com.intuit.profilevalidationsystem.exceptions.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ErrorResponse
{
    private String errorCode;

    private String errorMessage;

}
