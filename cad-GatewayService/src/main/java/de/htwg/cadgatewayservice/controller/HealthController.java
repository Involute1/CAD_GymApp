package de.htwg.cadgatewayservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/healthcheck")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public boolean healthcheck() {
        return true;
    }
}
