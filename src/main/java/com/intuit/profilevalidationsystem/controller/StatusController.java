package com.intuit.profilevalidationsystem.controller;

import com.intuit.profilevalidationsystem.dto.ProfileDTO;
import com.intuit.profilevalidationsystem.dto.response.UpdateStatusDTO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

public interface StatusController {

    @ApiOperation("Get Update Profile Status")
    @PostMapping(path = "/{id}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UpdateStatusDTO> getUpdateStatus(@PathVariable("id") UUID requestId);

}
