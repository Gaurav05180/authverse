package com.nic.resource_server.controller;

import com.nic.resource_server.model.GenericResponse;
import com.nic.resource_server.model.UserProfile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/profile")
    public UserProfile getProfile(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
//        String username = jwt.getClaimAsString("username");
        String email = username + "@example.com";
        List<String> roles = jwt.getClaimAsStringList("roles");
        return new UserProfile(username, email, roles);
    }

    @GetMapping("/dashboard")
    public GenericResponse getDashboard(@AuthenticationPrincipal Jwt jwt) {
            String message = "Welcome to your dashboard, " + jwt.getClaimAsString("username");
            return new GenericResponse(message, "success");
    }
}