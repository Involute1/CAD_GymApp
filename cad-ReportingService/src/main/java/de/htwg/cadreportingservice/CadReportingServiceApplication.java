package de.htwg.cadreportingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CadReportingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CadReportingServiceApplication.class, args);
    }

}
