package de.htwg.cadauthservice.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoogleAuthController {

    @GetMapping("/auth")
    public void healthcheck() throws FirebaseAuthException {

        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail("test@test.com")
                .setPassword("123456");

        FirebaseAuth defaultAuth = FirebaseAuth.getInstance();
        UserRecord createdRecord = defaultAuth.createUser(request);
        System.out.println(createdRecord.getUid());
    }
}
