package de.htwg.cadreportingservice.controller;

import de.htwg.cadreportingservice.service.ReportingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class ReportController {

    ReportingService reportingService;

    @GetMapping("/{uid}/{tenantId}")
    public boolean createReport(@PathVariable String uid, @PathVariable String tenantId) throws IOException, InterruptedException {
        this.reportingService.sendWorkoutToUser(uid, tenantId);
        return true;
    }
}
