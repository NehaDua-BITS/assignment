package com.intuit.profilevalidationsystem.controller.impl;

import com.intuit.profilevalidationsystem.controller.ProfileController;
import com.intuit.profilevalidationsystem.dto.ProfileDTO;
import com.intuit.profilevalidationsystem.dto.response.SubmitResponseDTO;
import com.intuit.profilevalidationsystem.helper.Validator;
import com.intuit.profilevalidationsystem.model.Profile;
import com.intuit.profilevalidationsystem.service.impl.ProfileServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/profile")
@Slf4j
public class ProfileControllerImpl implements ProfileController {

    private final ProfileServiceImpl profileService;

    @Autowired
    public ProfileControllerImpl(ProfileServiceImpl profileService) {
        this.profileService = profileService;
    }

    /**
     * To create profile
     * @param profileDTO
     * @return
     */
    @Override
    public ResponseEntity<Profile> createProfile(ProfileDTO profileDTO) {
        log.info("Create profile request received : " + profileDTO);
        Validator.validateCreateProfileRequest(profileDTO);
        Profile response = profileService.createProfile(profileDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * To accept update profile request from UI
     * @param profileId
     * @param profileDTO
     * @return
     */
    @Override
    public ResponseEntity<SubmitResponseDTO> acceptUpdateRequest(UUID profileId, ProfileDTO profileDTO, String source) {
        log.info("Update request received : " + profileDTO);
        Validator.validateUpdateProfileRequest(profileDTO, source);
        String requestId = profileService.submitUpdateRequest(profileId, profileDTO, source);
        return new ResponseEntity<>(new SubmitResponseDTO(requestId), HttpStatus.ACCEPTED);
    }

    /**
     * To fetch profile details for given business profile id
     * @param profileId
     * @return
     */
    @Override
    public ResponseEntity<Profile> getProfileById(UUID profileId) {
        return new ResponseEntity<>(profileService.getProfile(profileId), HttpStatus.OK);
    }

    /**
     * To update details of given profile id
     * @param profileId
     * @param profileDTO
     * @return
     */
    @Override
    public ResponseEntity<Profile> updateProfile(UUID profileId, ProfileDTO profileDTO) {
        Profile profile = profileService.updateProfile(profileId, profileDTO);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

}
