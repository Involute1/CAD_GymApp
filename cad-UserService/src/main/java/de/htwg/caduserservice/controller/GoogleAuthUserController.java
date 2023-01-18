package de.htwg.caduserservice.controller;

import com.google.firebase.auth.*;
import com.google.firebase.auth.multitenancy.TenantAwareFirebaseAuth;
import de.htwg.caduserservice.model.UserData;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class GoogleAuthUserController {
    private static final Log LOGGER = LogFactory.getLog(GoogleAuthUserController.class);
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

    @PostMapping("/register/{tenantId}")
    public UserData registerUserWithTenant(UserData userData, @PathVariable String tenantId) {
        if (StringUtils.isBlank(tenantId)) {
            LOGGER.error("tenantId is null or empty on registerUserWithTenant");
            return null;
        }
        if (StringUtils.isBlank(userData.displayName()) || StringUtils.isBlank(userData.email()) || StringUtils.isBlank(userData.password())) {
            LOGGER.error("userData has empty values on registerUserWithTenant");
            return null;
        }
        TenantAwareFirebaseAuth tenantAuth = FirebaseAuth.getInstance().getTenantManager()
                .getAuthForTenant(tenantId);
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(userData.email())
                .setDisplayName(userData.displayName())
                .setPassword(userData.password());
        try {
            UserRecord createdUser = tenantAuth.createUser(request);
            return new UserData(UUID.fromString(createdUser.getUid()), createdUser.getEmail(), "", createdUser.getDisplayName(), createdUser.getPhoneNumber(), createdUser.isEmailVerified(), createdUser.isDisabled(), createdUser.getPhotoUrl(), createdUser.getTenantId());
        } catch (FirebaseAuthException e) {
            LOGGER.error(e.getMessage());
            return null;
        }

    }

    @GetMapping("/all")
    public List<UserData> getAllUserForTenant(String tenantId) {
        TenantAwareFirebaseAuth tenantAuth = FirebaseAuth.getInstance().getTenantManager()
                .getAuthForTenant(tenantId);
        try {
            List<UserData> userDataList = new ArrayList<>();
            ListUsersPage users = tenantAuth.listUsers(null);
            users.iterateAll().forEach(user -> userDataList.add(new UserData(UUID.fromString(user.getUid()), user.getEmail(), "", user.getDisplayName(), user.getPhoneNumber(), user.isEmailVerified(), user.isDisabled(), user.getPhotoUrl(), user.getTenantId())));
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
            return new UserData(UUID.fromString(user.getUid()), user.getEmail(), "", user.getDisplayName(), user.getPhoneNumber(), user.isEmailVerified(), user.isDisabled(), user.getPhotoUrl(), user.getTenantId());
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
        if (StringUtils.isBlank(updatedUserData.displayName()) || StringUtils.isBlank(updatedUserData.email()) || StringUtils.isBlank(updatedUserData.password())) {
            LOGGER.error("updatedUserData has empty values on updateUser");
            return null;
        }
        TenantAwareFirebaseAuth tenantAuth = FirebaseAuth.getInstance().getTenantManager()
                .getAuthForTenant(tenantId);

        UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(uid)
                .setEmail(updatedUserData.email())
                .setPhoneNumber(updatedUserData.phoneNumber())
                .setEmailVerified(updatedUserData.isEmailVerified())
                .setPassword(updatedUserData.password())
                .setDisplayName(updatedUserData.displayName())
                .setPhotoUrl(updatedUserData.photoUrl())
                .setDisabled(updatedUserData.isDisabled());
        try {
            UserRecord userRecord = tenantAuth.updateUser(request);
            return new UserData(UUID.fromString(userRecord.getUid()), userRecord.getEmail(), "", userRecord.getDisplayName(), userRecord.getPhoneNumber(), userRecord.isEmailVerified(), userRecord.isDisabled(), userRecord.getPhotoUrl(), userRecord.getTenantId());
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
