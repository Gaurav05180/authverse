package com.nic.auth_server.repository;


import com.nic.auth_server.entity.OAuthClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuthClientRepository extends JpaRepository<OAuthClient, String> {
}
