package de.htwg.caduserservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.*;
import com.google.firebase.auth.multitenancy.TenantAwareFirebaseAuth;
import de.htwg.caduserservice.model.*;
import de.htwg.caduserservice.repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class GoogleAuthUserController {
    private static final Log LOGGER = LogFactory.getLog(GoogleAuthUserController.class);
    private static final String DEFAULT_GYM_SERVICE_URL = "http://localhost:7081";

    private IUserRepository userRepository;
    private GoogleAuthTenantController googleAuthTenantController;
    //https://cloud.google.com/identity-platform/docs/multi-tenancy-managing-tenants#creating_a_user

    @GetMapping("/verify")
    public boolean verifyToken(String idToken, String tenantId) {
        if (StringUtils.isBlank(idToken)) {
            LOGGER.error("idToken is null or empty on verifyToken");
            return false;
        }
        if (StringUtils.isBlank(tenantId)) {
            LOGGER.error("tenantId is null or empty on verifyToken");
            return false;
        }
        TenantAwareFirebaseAuth tenantAuth = FirebaseAuth.getInstance().getTenantManager()
                .getAuthForTenant(tenantId);
        try {
            // idToken comes from the client app
            FirebaseToken token = tenantAuth.verifyIdToken(idToken);
            // TenantId on the FirebaseToken should be set to TENANT-ID.
            // Otherwise, "tenant-id-mismatch" error thrown.
            return true;
        } catch (FirebaseAuthException e) {
            LOGGER.error("error verifying ID token: " + e.getMessage());
            return false;
        }
    }

    @PostMapping("/user")
    public UserData registerUserWithTenant(@RequestBody RegisterUserData registerUserData) throws IOException, InterruptedException {
        LOGGER.info(registerUserData);
        String tenantId;
        if (registerUserData.role().equals(Roles.GymOwner)) {
            tenantId = googleAuthTenantController.registerTenant(registerUserData.gymName()).tenantId();


            String gymServiceUrl = System.getenv("GYM_SERVICE_URL");
            if (gymServiceUrl == null) {
                gymServiceUrl = DEFAULT_GYM_SERVICE_URL;
            }

            var objectMapper = new ObjectMapper();
            String requestBody = objectMapper
                    .writeValueAsString(new Gym(registerUserData.gymName(), "Description", registerUserData.billingModel()));

            HttpClient httpClient = HttpClient.newBuilder().build();
            HttpRequest gymsRequest = HttpRequest.newBuilder()
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .uri(URI.create(gymServiceUrl))
                    .build();
            httpClient.send(gymsRequest, HttpResponse.BodyHandlers.ofString());
        } else {
            tenantId = registerUserData.tenantId();
        }

        if (StringUtils.isBlank(tenantId)) {
            LOGGER.error("tenantId is null or empty on registerUserWithTenant");
            return null;
        }
        if (StringUtils.isBlank(registerUserData.displayName()) || StringUtils.isBlank(registerUserData.email()) || StringUtils.isBlank(registerUserData.password())) {
            LOGGER.error("registerUserData has empty values on registerUserWithTenant");
            return null;
        }
        TenantAwareFirebaseAuth tenantAuth = FirebaseAuth.getInstance().getTenantManager()
                .getAuthForTenant(tenantId);

        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(registerUserData.email())
                .setDisplayName(registerUserData.displayName())
                .setPassword(registerUserData.password());

        try {
            UserRecord createdUser = tenantAuth.createUser(request);
            LOGGER.info(createdUser.getUid());
            User newUser = new User(createdUser.getUid(), registerUserData.role());
            User savedUser = userRepository.save(newUser);
            return new UserData(savedUser.getRole(), createdUser.getUid(), createdUser.getEmail(), createdUser.getDisplayName(), createdUser.isEmailVerified(), createdUser.getTenantId());
        } catch (FirebaseAuthException e) {
            LOGGER.error(e.getMessage());
            return null;
        }

    }

    @GetMapping("/all/{tenantId}")
    public List<UserData> getAllUserForTenant(@PathVariable String tenantId) {
        TenantAwareFirebaseAuth tenantAuth = FirebaseAuth.getInstance().getTenantManager()
                .getAuthForTenant(tenantId);
        try {
            List<UserData> userDataList = new ArrayList<>();
            ListUsersPage users = tenantAuth.listUsers(null);
            users.iterateAll().forEach(user -> {
                User savedUser = userRepository.findByUid(user.getUid());
                userDataList.add(new UserData(savedUser.getRole(), user.getUid(), user.getEmail(), user.getDisplayName(), user.isEmailVerified(), user.getTenantId()));
            });
            return userDataList;
        } catch (FirebaseAuthException e) {
            LOGGER.error(e.getMessage());
            return new ArrayList<>();
        }

    }

    @GetMapping("/{uid}/{tenantId}")
    public UserData getUser(@PathVariable String uid, @PathVariable String tenantId) {
        if (StringUtils.isBlank(uid)) {
            LOGGER.error("UID was empty for getUser");
            return null;
        }
        if (StringUtils.isBlank(tenantId)) {
            LOGGER.error("tenantId is null or empty on getUser");
            return null;
        }
        TenantAwareFirebaseAuth tenantAuth = FirebaseAuth.getInstance().getTenantManager()
                .getAuthForTenant(tenantId);
        try {
            UserRecord user = tenantAuth.getUser(uid);
            User savedUser = userRepository.findByUid(user.getUid());
            return new UserData(savedUser.getRole(), user.getUid(), user.getEmail(), user.getDisplayName(), user.isEmailVerified(), user.getTenantId());
        } catch (FirebaseAuthException e) {
            LOGGER.error(e.getMessage());
            return null;
        }

    }

    @PatchMapping("/{uid}/{tenantId}")
    public UserData updateUser(UserData updatedUserData, @PathVariable String tenantId, @PathVariable String uid) {
        if (StringUtils.isBlank(uid)) {
            LOGGER.error("UID was empty for updateUser");
            return null;
        }
        if (StringUtils.isBlank(tenantId)) {
            LOGGER.error("tenantId is null or empty on updateUser");
            return null;
        }
        if (StringUtils.isBlank(updatedUserData.displayName())) {
            LOGGER.error("updatedUserData has empty values on updateUser");
            return null;
        }
        TenantAwareFirebaseAuth tenantAuth = FirebaseAuth.getInstance().getTenantManager()
                .getAuthForTenant(tenantId);

        UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(uid)
                .setDisplayName(updatedUserData.displayName());
        try {
            UserRecord userRecord = tenantAuth.updateUser(request);
            User savedUser = userRepository.findByUid(userRecord.getUid());
            return new UserData(savedUser.getRole(), userRecord.getUid(), userRecord.getEmail(), userRecord.getDisplayName(), userRecord.isEmailVerified(), userRecord.getTenantId());
        } catch (FirebaseAuthException e) {
            LOGGER.error(e.getMessage());
            return null;
        }

    }

    @DeleteMapping("/{uid}/{tenantId}")
    public boolean deleteUser(@PathVariable String uid, @PathVariable String tenantId) {
        if (uid.isEmpty()) {
            LOGGER.error("UID was empty for deleteUser");
            return false;
        }
        if (StringUtils.isBlank(tenantId)) {
            LOGGER.error("tenantId is null or empty on deleteUser");
            return false;
        }
        TenantAwareFirebaseAuth tenantAuth = FirebaseAuth.getInstance().getTenantManager()
                .getAuthForTenant(tenantId);
        try {
            tenantAuth.deleteUser(uid);
            return true;
        } catch (FirebaseAuthException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }


}
