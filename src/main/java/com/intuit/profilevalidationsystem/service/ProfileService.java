package com.intuit.profilevalidationsystem.service;

import com.intuit.profilevalidationsystem.dto.ProfileDTO;
import com.intuit.profilevalidationsystem.kafka.UpdateEvent;
import com.intuit.profilevalidationsystem.model.Profile;

import java.util.UUID;

public interface ProfileService {

    Profile getProfile(UUID profileId);

    Profile createProfile(ProfileDTO profileDTO);

    String submitUpdateRequest(UUID profileId, ProfileDTO profileDTO, String source);

    Profile updateProfile(UUID profileId, ProfileDTO profileDTO);

}
