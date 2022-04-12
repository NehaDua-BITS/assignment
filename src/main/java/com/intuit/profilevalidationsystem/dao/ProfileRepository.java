package com.intuit.profilevalidationsystem.dao;

import com.intuit.profilevalidationsystem.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID> {

}
