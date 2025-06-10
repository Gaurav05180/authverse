package com.nic.auth_server.controller;

import com.nic.auth_server.repository.UserRepository;
import com.nic.auth_server.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class UserInfoController {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @GetMapping("/userinfo")
    public Map<String, Object> userInfo(@RequestHeader("Authorization") String authHeader) {
        Map<String, Object> response = new HashMap<>();

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.put("error", "Missing or invalid Authorization header");
            return response;
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.validateToken(token)) {
            response.put("error", "Invalid or expired token");
            return response;
        }

        String username = jwtUtil.extractUsername(token);
        var user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            response.put("error", "User not found");
            return response;
        }

        response.put("username", user.getUsername());
        response.put("roles", user.getRoles());
        return response;
    }
}