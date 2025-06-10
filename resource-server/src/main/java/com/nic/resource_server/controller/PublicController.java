package com.nic.resource_server.controller;

import com.nic.resource_server.model.GenericResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @GetMapping("/info")
    public GenericResponse getInfo() {
        return new GenericResponse("Resource Server", "public endpoint");
    }
}