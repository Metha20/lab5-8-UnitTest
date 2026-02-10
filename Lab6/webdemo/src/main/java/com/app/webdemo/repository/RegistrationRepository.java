package com.app.webdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.webdemo.model.RegistrationEntity;

public interface RegistrationRepository
        extends JpaRepository<RegistrationEntity, Long> {
}
