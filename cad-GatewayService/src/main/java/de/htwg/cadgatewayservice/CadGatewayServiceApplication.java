package de.htwg.cadgatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CadGatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CadGatewayServiceApplication.class, args);
        System.out.println(System.getenv("GYM_SERVICE_URL"));
        System.out.println(System.getenv("USER_SERVICE_URL"));
        System.out.println(System.getenv("WORKOUT_SERVICE_URL"));
        System.out.println(System.getenv("REPORTING_SERVICE_URL"));
    }

}
