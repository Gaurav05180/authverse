package com.nic.auth_server.repository;

import com.nic.auth_server.entity.AuthorizationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorizationCodeRepository extends JpaRepository<AuthorizationCode, String> {
}