package de.htwg.caduserservice.controller;

import com.google.firebase.auth.*;
import com.google.firebase.auth.multitenancy.TenantAwareFirebaseAuth;
import de.htwg.caduserservice.model.UserData;
import de.htwg.caduserservice.util.LoggerUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GoogleAuthUserController {

    //https://cloud.google.com/identity-platform/docs/multi-tenancy-managing-tenants#creating_a_user

    @GetMapping("/verify")
    public boolean verifyToken(String idToken, String tenantId) {
        if (StringUtils.isBlank(idToken)) {
            LoggerUtil.log("idToken is null or empty on verifyToken");
            return false;
        }
        if (StringUtils.isBlank(tenantId)) {
            LoggerUtil.log("tenantId is null or empty on verifyToken");
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
            LoggerUtil.log("error verifying ID token: " + e.getMessage());
            return false;
        }
    }

    @PostMapping("/register/{tenantId}")
    public UserData registerUserWithTenant(UserData userData, @PathVariable String tenantId) {
        if (StringUtils.isBlank(tenantId)) {
            LoggerUtil.log("tenantId is null or empty on registerUserWithTenant");
            return null;
        }
        if (StringUtils.isBlank(userData.displayName()) || StringUtils.isBlank(userData.email()) || StringUtils.isBlank(userData.password())) {
            LoggerUtil.log("userData has empty values on registerUserWithTenant");
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
            return new UserData(createdUser.getUid(), createdUser.getEmail(), "", createdUser.getDisplayName(), createdUser.getPhoneNumber(), createdUser.isEmailVerified(), createdUser.isDisabled(), createdUser.getPhotoUrl(), createdUser.getTenantId());
        } catch (FirebaseAuthException e) {
            LoggerUtil.log(e.getMessage());
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
            users.iterateAll().forEach(user -> userDataList.add(new UserData(user.getUid(), user.getEmail(), "", user.getDisplayName(), user.getPhoneNumber(), user.isEmailVerified(), user.isDisabled(), user.getPhotoUrl(), user.getTenantId())));
            return userDataList;
        } catch (FirebaseAuthException e) {
            LoggerUtil.log(e.getMessage());
            return new ArrayList<>();
        }

    }

    @GetMapping("/{uid}/{tenantId}")
    public UserData getUser(@PathVariable String uid, @PathVariable String tenantId) {
        if (StringUtils.isBlank(uid)) {
            LoggerUtil.log("UID was empty for getUser");
            return null;
        }
        if (StringUtils.isBlank(tenantId)) {
            LoggerUtil.log("tenantId is null or empty on getUser");
            return null;
        }
        TenantAwareFirebaseAuth tenantAuth = FirebaseAuth.getInstance().getTenantManager()
                .getAuthForTenant(tenantId);
        try {
            UserRecord user = tenantAuth.getUser(uid);
            return new UserData(user.getUid(), user.getEmail(), "", user.getDisplayName(), user.getPhoneNumber(), user.isEmailVerified(), user.isDisabled(), user.getPhotoUrl(), user.getTenantId());
        } catch (FirebaseAuthException e) {
            LoggerUtil.log(e.getMessage());
            return null;
        }

    }

    @PatchMapping("/{uid}/{tenantId}")
    public UserData updateUser(UserData updatedUserData, @PathVariable String tenantId, @PathVariable String uid) {
        if (StringUtils.isBlank(uid)) {
            LoggerUtil.log("UID was empty for updateUser");
            return null;
        }
        if (StringUtils.isBlank(tenantId)) {
            LoggerUtil.log("tenantId is null or empty on updateUser");
            return null;
        }
        if (StringUtils.isBlank(updatedUserData.displayName()) || StringUtils.isBlank(updatedUserData.email()) || StringUtils.isBlank(updatedUserData.password())) {
            LoggerUtil.log("updatedUserData has empty values on updateUser");
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
            return new UserData(userRecord.getUid(), userRecord.getEmail(), "", userRecord.getDisplayName(), userRecord.getPhoneNumber(), userRecord.isEmailVerified(), userRecord.isDisabled(), userRecord.getPhotoUrl(), userRecord.getTenantId());
        } catch (FirebaseAuthException e) {
            LoggerUtil.log(e.getMessage());
            return null;
        }

    }

    @DeleteMapping("/{uid}/{tenantId}")
    public boolean deleteUser(@PathVariable String uid, @PathVariable String tenantId) {
        if (uid.isEmpty()) {
            LoggerUtil.log("UID was empty for deleteUser");
            return false;
        }
        if (StringUtils.isBlank(tenantId)) {
            LoggerUtil.log("tenantId is null or empty on deleteUser");
            return false;
        }
        TenantAwareFirebaseAuth tenantAuth = FirebaseAuth.getInstance().getTenantManager()
                .getAuthForTenant(tenantId);
        try {
            tenantAuth.deleteUser(uid);
            return true;
        } catch (FirebaseAuthException e) {
            LoggerUtil.log(e.getMessage());
            return false;
        }
    }


}
