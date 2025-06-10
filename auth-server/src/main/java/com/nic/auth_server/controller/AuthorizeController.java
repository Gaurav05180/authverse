package com.nic.auth_server.controller;

import com.nic.auth_server.entity.AuthorizationCode;
import com.nic.auth_server.entity.OAuthClient;
import com.nic.auth_server.entity.User;
import com.nic.auth_server.repository.AuthorizationCodeRepository;
import com.nic.auth_server.repository.OAuthClientRepository;
import com.nic.auth_server.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AuthorizeController {

    private final OAuthClientRepository clientRepo;
    private final UserRepository userRepo;
    private final AuthorizationCodeRepository codeRepo;

    @GetMapping("/authorize")
    public String authorize(
            @RequestParam String response_type,
            @RequestParam String client_id,
            @RequestParam String redirect_uri,
            Model model,
            Authentication auth,
            HttpSession session
    ) {
        if (!"code".equals(response_type)) {
            model.addAttribute("error", "Only 'code' response_type is supported.");
            return "error";
        }

        OAuthClient client = clientRepo.findById(client_id).orElse(null);
        if (client == null || !client.getRedirectUri().equals(redirect_uri)) {
            model.addAttribute("error", "Invalid client ID or redirect URI.");
            return "error";
        }

        model.addAttribute("clientId", client_id);
        model.addAttribute("redirectUri", redirect_uri);
        return "consent";
    }

    @PostMapping("/authorize")
    public String handleConsent(
            @RequestParam String client_id,
            @RequestParam String redirect_uri,
            Authentication auth
    ) {
        String username = auth.getName();
        User user = userRepo.findByUsername(username).orElseThrow();

        OAuthClient client = clientRepo.findById(client_id).orElseThrow();

        String code = UUID.randomUUID().toString();
        AuthorizationCode authCode = AuthorizationCode.builder()
                .code(code)
                .user(user)
                .client(client)
                .expiryTime(LocalDateTime.now().plusMinutes(10))
                .build();

        codeRepo.save(authCode);

        return "redirect:" + redirect_uri + "?code=" + code;
    }
}