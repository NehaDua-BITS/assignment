package com.intuit.profilevalidationsystem.service;

import com.intuit.profilevalidationsystem.dto.ProfileDTO;

import java.util.UUID;

public interface ProfileService {

    ProfileDTO createProfile(ProfileDTO profileDTO);

    void submitUpdateRequest(UUID profileId, ProfileDTO profileDTO, String source);

    void updateProfile(UUID profileId, ProfileDTO profileDTO);

}
