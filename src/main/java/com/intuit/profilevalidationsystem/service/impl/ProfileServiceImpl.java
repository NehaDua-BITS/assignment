package com.intuit.profilevalidationsystem.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.intuit.profilevalidationsystem.dao.ProfileRepository;
import com.intuit.profilevalidationsystem.dto.ProfileDTO;
import com.intuit.profilevalidationsystem.exceptions.types.InvalidInputException;
import com.intuit.profilevalidationsystem.exceptions.types.SubmitRequestException;
import com.intuit.profilevalidationsystem.exceptions.constants.ErrorCodes;
import com.intuit.profilevalidationsystem.helper.Mapper;
import com.intuit.profilevalidationsystem.helper.ProfileMapper;
import com.intuit.profilevalidationsystem.kafka.Producer;
import com.intuit.profilevalidationsystem.kafka.UpdateEvent;
import com.intuit.profilevalidationsystem.model.Profile;
import com.intuit.profilevalidationsystem.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ProfileServiceImpl implements ProfileService {

    private EventServiceImpl eventService;

    private final Producer producer;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    public ProfileServiceImpl(EventServiceImpl eventService, Producer producer, ProfileRepository profileRepository) {
        this.eventService = eventService;
        this.producer = producer;
    }

    @Override
    public Profile getProfile(UUID profileId) {
        log.info("Fetching profile for id : " + profileId);
        Optional<Profile> optionalProfile = profileRepository.findById(profileId);
        if (!optionalProfile.isPresent()) {
            throw new InvalidInputException(ErrorCodes.NO_PROFILE_FOUND);
        }
        return optionalProfile.get();
    }

    @Override
    public Profile createProfile(ProfileDTO profileDTO) {
        log.info("Creating profile");
        Profile profile = profileRepository.save(ProfileMapper.mapProfileDTO(profileDTO));
        return profile;
    }

    public String submitUpdateRequest(UUID profileId, ProfileDTO profileDTO, String source) {
        try {
            UpdateEvent event = eventService.prepareEvent(profileId, profileDTO, source);
            producer.sendMessage(Mapper.toJson(event));
            log.info("Successfully sent event to Kafka : " + event);
            return String.valueOf(event.getRequestId());
        } catch (JsonProcessingException e) {
            log.error("Failed to prepare update event : " + e.getMessage());
            throw new SubmitRequestException(ErrorCodes.SUBMIT_UPDATE_REQUEST_FAILED, e.getMessage());
        }
    }

    @Override
    public Profile updateProfile(UUID profileId, ProfileDTO profileDTO) {
        log.info("Updating profile");
        Optional<Profile> optional = profileRepository.findById(profileId);
        if (!optional.isPresent()) {
            throw new InvalidInputException(ErrorCodes.NO_PROFILE_FOUND, profileId);
        }
        Profile profile = optional.get();
        profile = ProfileMapper.mapDTOtoModel(profileDTO, profile);
        return profileRepository.save(profile);
    }

}
