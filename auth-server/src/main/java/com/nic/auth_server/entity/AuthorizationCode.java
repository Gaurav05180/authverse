package com.nic.auth_server.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorizationCode {

    @Id
    private String code;

    @ManyToOne
    private User user;

    @ManyToOne
    private OAuthClient client;

    private LocalDateTime expiryTime;
}
