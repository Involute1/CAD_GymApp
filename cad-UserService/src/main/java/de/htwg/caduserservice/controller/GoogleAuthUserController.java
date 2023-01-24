package de.htwg.caduserservice.controller;

import com.google.firebase.auth.*;
import com.google.firebase.auth.multitenancy.TenantAwareFirebaseAuth;
import de.htwg.caduserservice.model.User;
import de.htwg.caduserservice.model.UserData;
import de.htwg.caduserservice.model.requests.RegisterUserData;
import de.htwg.caduserservice.model.requests.VerifyTokenData;
import de.htwg.caduserservice.repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class GoogleAuthUserController {
    private static final Log LOGGER = LogFactory.getLog(GoogleAuthUserController.class);

    private IUserRepository userRepository;
    private GoogleAuthTenantController googleAuthTenantController;
    //https://cloud.google.com/identity-platform/docs/multi-tenancy-managing-tenants#creating_a_user

    @PostMapping("/verify")
    public boolean verifyToken(@RequestBody VerifyTokenData verifyTokenData) {
        if (StringUtils.isBlank(verifyTokenData.idToken())) {
            LOGGER.error("idToken is null or empty on verifyToken");
            return false;
        }
        if (StringUtils.isBlank(verifyTokenData.tenantId())) {
            LOGGER.error("tenantId is null or empty on verifyToken");
            return false;
        }
        TenantAwareFirebaseAuth tenantAuth = FirebaseAuth.getInstance().getTenantManager()
                .getAuthForTenant(verifyTokenData.tenantId());
        try {
            // idToken comes from the client app
            FirebaseToken token = tenantAuth.verifyIdToken(verifyTokenData.idToken());
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
        if (StringUtils.isBlank(registerUserData.tenantId())) {
            LOGGER.error("tenantId is null or empty on registerUserWithTenant");
            return null;
        }
        if (StringUtils.isBlank(registerUserData.displayName()) || StringUtils.isBlank(registerUserData.email()) || StringUtils.isBlank(registerUserData.password())) {
            LOGGER.error("registerUserData has empty values on registerUserWithTenant");
            return null;
        }
        TenantAwareFirebaseAuth tenantAuth = FirebaseAuth.getInstance().getTenantManager()
                .getAuthForTenant(registerUserData.tenantId());

        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(registerUserData.email())
                .setDisplayName(registerUserData.displayName())
                .setEmailVerified(true)
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
