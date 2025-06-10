package com.nic.auth_server.controller;

import com.nic.auth_server.entity.AuthorizationCode;
import com.nic.auth_server.entity.OAuthClient;
import com.nic.auth_server.repository.AuthorizationCodeRepository;
import com.nic.auth_server.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class TokenController {

    private final AuthorizationCodeRepository codeRepo;
    private final JwtUtil jwtUtil;

    @PostMapping("/token")
    public Map<String, Object> getToken(
            @RequestParam String code,
            @RequestParam String client_id,
            @RequestParam String client_secret,
            @RequestParam String redirect_uri,
            @RequestParam String grant_type
    ) {
        Map<String, Object> response = new HashMap<>();

        if (!"authorization_code".equals(grant_type)) {
            response.put("error", "unsupported_grant_type");
            return response;
        }

        AuthorizationCode authCode = codeRepo.findById(code).orElse(null);
        if (authCode == null || authCode.getExpiryTime().isBefore(LocalDateTime.now())) {
            response.put("error", "invalid_or_expired_code");
            return response;
        }

        OAuthClient client = authCode.getClient();
        if (!client.getClientId().equals(client_id) || !client.getClientSecret().equals(client_secret)
                || !client.getRedirectUri().equals(redirect_uri)) {
            response.put("error", "invalid_client_credentials");
            return response;
        }

        String token = jwtUtil.generateToken(authCode.getUser().getUsername(), authCode.getUser().getRoles());

        response.put("access_token", token);
        response.put("token_type", "Bearer");
        response.put("expires_in", 3600);

        codeRepo.delete(authCode); // Remove code once used (one-time use)

        return response;
    }
}