package com.nic.auth_server.repository;


import com.nic.auth_server.entity.OAuthClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuthClientRepository extends JpaRepository<OAuthClient, String> {
    Optional<OAuthClient> findByClientId(String clientId);
}
