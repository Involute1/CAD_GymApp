package de.htwg.cadauthservice;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
public class CadAuthServiceApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(CadAuthServiceApplication.class, args);
        initFirebaseConfig();
    }

    public static void initFirebaseConfig() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("W:\\HTWG_Master\\CAD_GymApp\\cad-AuthService\\src\\main\\resources\\cad-project-368216-10a1836bc192.json");
//        FileInputStream serviceAccount = new FileInputStream("W:\\HTWG_Master\\CAD_GymApp\\cad-AuthService\\src\\main\\resources\\authKey.json");


        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setProjectId("cad-project-368216")
                .build();

        FirebaseApp.initializeApp(options);
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
