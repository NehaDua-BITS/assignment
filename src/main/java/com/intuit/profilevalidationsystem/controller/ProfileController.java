package com.intuit.profilevalidationsystem.controller;

import com.intuit.profilevalidationsystem.dto.ProfileDTO;
import com.intuit.profilevalidationsystem.dto.response.SubmitResponseDTO;
import com.intuit.profilevalidationsystem.model.Profile;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

public interface ProfileController {

    @ApiOperation("Create Profile")
    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Profile> createProfile(@RequestBody ProfileDTO profileDTO);

    @ApiOperation("Submit Update Profile request")
    @PostMapping(path = "{id}/submit", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<SubmitResponseDTO> acceptUpdateRequest(@PathVariable("id") UUID profileId, @RequestBody ProfileDTO profileDTO,
                                                          @RequestHeader(name = "source", required = false) String source);

    @ApiOperation("Get Profile by customer Id")
    @GetMapping(value = "{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Profile> getProfileById(@PathVariable("id") UUID profileId);

    @ApiOperation("Update Profile")
    @PutMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Profile> updateProfile(@PathVariable("id") UUID profileId, @RequestBody ProfileDTO profileDTO);

}
