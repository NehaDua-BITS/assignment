package com.intuit.profilevalidationsystem.helper;

import com.intuit.profilevalidationsystem.dto.ProfileDTO;
import com.intuit.profilevalidationsystem.exceptions.InvalidInputException;
import com.intuit.profilevalidationsystem.exceptions.constants.ErrorCodes;
import org.springframework.util.StringUtils;

public class Validator {

    public static void validateCreateProfileRequest(ProfileDTO profileDTO) {
        if (profileDTO == null) {
            throw new InvalidInputException(ErrorCodes.INVALID_INPUT, "Profile DTO cannot be null");
        }

        if (profileDTO.getTaxIdentifiers() == null || profileDTO.getTaxIdentifiers().size() == 0) {
            throw new InvalidInputException(ErrorCodes.INVALID_INPUT, "Atleast 1 tax identifier of business is mandatory");
        }

        if (StringUtils.isEmpty(profileDTO.getName()) || StringUtils.isEmpty(profileDTO.getLegalName())
                    || StringUtils.isEmpty(profileDTO.getEmail())) {
            throw new InvalidInputException(ErrorCodes.INVALID_INPUT, "Name, legal name and email id cannot be empty");
        }

    }

    public static void validateUpdateProfileRequest(ProfileDTO profileDTO, String source) {
        if (profileDTO == null || source == null || StringUtils.isEmpty(source)) {
            throw new InvalidInputException(ErrorCodes.INVALID_INPUT, "Profile DTO and source cannot be null");
        }

        if (isValueEmpty(profileDTO.getLegalName())) {
            throw new InvalidInputException(ErrorCodes.INVALID_INPUT, "Legal name cannot be set blank");
        }

        if (isValueEmpty(profileDTO.getName())) {
            throw new InvalidInputException(ErrorCodes.INVALID_INPUT, "Business name cannot be set blank");
        }

        if (isValueEmpty(profileDTO.getEmail())) {
            throw new InvalidInputException(ErrorCodes.INVALID_INPUT, "Email cannot be set blank");
        }
    }

    public static boolean isValueEmpty(String name) {
        return name != null && StringUtils.isEmpty(name);
    }


}
