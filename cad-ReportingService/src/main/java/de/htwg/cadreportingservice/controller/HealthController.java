package de.htwg.cadreportingservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/healthcheck")
    public boolean healthcheck() {
        System.out.println("HELLO");
        return true;
    }
}
