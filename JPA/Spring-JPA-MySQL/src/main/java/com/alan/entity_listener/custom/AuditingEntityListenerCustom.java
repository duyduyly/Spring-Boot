package com.alan.entity_listener.custom;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public class AuditingEntityListenerCustom {


    //Check Email After Store into DB
    @PrePersist
    public void prePersist(Model2 e) {
        if (!e.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email is required!");
        }
        e.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate(Model2 e) {
        e.setUpdatedAt(LocalDateTime.now());
    }


    //Send mail after create (Example User)
    @PostPersist
    public void notifyNewUser(Model2 e) {

        //send mail if you have notification Service
        System.out.println("New user created: " + e.getEmail());
    }


    @PreRemove
    public void preRemove(Model2 e) {
        log.info("Remove user: " + e.getEmail());
    }
}
