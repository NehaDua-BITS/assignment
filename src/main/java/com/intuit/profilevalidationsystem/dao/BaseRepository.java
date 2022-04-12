package com.intuit.profilevalidationsystem.dao;

import com.intuit.profilevalidationsystem.model.Profile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@EnableRetry
@Slf4j
public class BaseRepository {

    public Profile getProfileById(UUID id) {
        return null;
    }

    public Profile addProfile(UUID id, Profile profile) {
        return null;
    }

    public Profile updateProfile(UUID id, String query) {
        return null;
    }

}
