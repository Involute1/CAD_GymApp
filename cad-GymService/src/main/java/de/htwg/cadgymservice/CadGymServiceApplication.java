package de.htwg.cadgymservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
//@ComponentScan("de.htwg.cadgymservice.repository")
//@EntityScan("de.htwg.cadgymservice.model")
//@EnableReactiveFirestoreRepositories(basePackages = {"de.htwg.cadgymservice.repository"})
public class CadGymServiceApplication {

    public static void main(String[] args) {
        System.setProperty("GOOGLE_CLOUD_PROJECT", "cad-gym-app");
        SpringApplication.run(CadGymServiceApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }
}
