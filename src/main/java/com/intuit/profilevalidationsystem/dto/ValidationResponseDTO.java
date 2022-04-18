package com.intuit.profilevalidationsystem.dto;

import com.intuit.profilevalidationsystem.constants.ValidationResponse;
import com.intuit.profilevalidationsystem.exceptions.model.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationResponseDTO {

    private ValidationResponse validationResponse;

    private ErrorResponse error;

}
