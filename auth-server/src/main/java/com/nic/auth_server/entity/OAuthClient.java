package com.nic.auth_server.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OAuthClient {

    @Id
    private String clientId;

    private String clientSecret;

    private String redirectUri;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> scopes;
}
