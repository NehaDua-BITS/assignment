package com.intuit.profilevalidationsystem.controller.impl;

import com.intuit.profilevalidationsystem.controller.ProfileController;
import com.intuit.profilevalidationsystem.dto.ProfileDTO;
import com.intuit.profilevalidationsystem.helper.Validator;
import com.intuit.profilevalidationsystem.model.BusinessProfile;
import com.intuit.profilevalidationsystem.service.impl.ProfileServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/profile")
@Slf4j
public class ProfileControllerImpl implements ProfileController {

    private final ProfileServiceImpl profileService;

    @Autowired
    public ProfileControllerImpl(ProfileServiceImpl profileService) {
        this.profileService = profileService;
    }

    /**
     * To check health of the controller
     * @return
     */
    @Override
    public ResponseEntity healthCheck() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProfileDTO> createProfile(ProfileDTO profileDTO) {
        log.info("Create profile request received : " + profileDTO);
        Validator.validateCreateProfileRequest(profileDTO);
        ProfileDTO responseDTO = profileService.createProfile(profileDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    /**
     * To accept update profile request from UI
     * @param profileId
     * @param profileDTO
     * @return
     */
    @Override
    public ResponseEntity<ProfileDTO> acceptUpdateRequest(UUID profileId, ProfileDTO profileDTO, String source) {
        log.info("Update request received : " + profileDTO);
        Validator.validateUpdateProfileRequest(profileDTO, source);
        profileService.submitUpdateRequest(profileId, profileDTO, source);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * To fetch profile details for given business profile id
     * @param profileId
     * @return
     */
    @Override
    public ResponseEntity<BusinessProfile> getProfileById(UUID profileId) {
        //return new ResponseEntity<>(accountService.getAccountById(accountId.toString()), HttpStatus.OK);
        return null;
    }

    /**
     * To update details of given profile id
     * @param profileId
     * @param profileDTO
     * @return
     */
    @Override
    public ResponseEntity<ProfileDTO> updateProfile(UUID profileId, ProfileDTO profileDTO) {
        return null;
    }

}
