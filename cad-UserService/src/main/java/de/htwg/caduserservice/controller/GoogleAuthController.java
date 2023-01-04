package de.htwg.caduserservice.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.ListUsersPage;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.multitenancy.Tenant;
import com.google.firebase.auth.multitenancy.TenantManager;
import de.htwg.caduserservice.model.User;
import de.htwg.caduserservice.util.LoggerUtil;
import org.springframework.web.bind.annotation.*;

@RestController
public class GoogleAuthController {

    @PostMapping("/user")
    public String registerUserWithTenant(User user) {
        //TODO var checks
        // create tenant and get id from it to use
        // maybe return user?

        FirebaseAuth defaultAuth = FirebaseAuth.getInstance();
        UserRecord createdRecord = null;

        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(user.email())
                .setDisplayName(user.displayName())
                .setPassword(user.password());

        try {
            createdRecord = defaultAuth.createUser(request);
        } catch (FirebaseAuthException e) {
            LoggerUtil.log(e.getMessage());
            return null;
        }

        return createdRecord.getUid();
    }

    @GetMapping("/users")
    public void getAllUser() {
        try {
            ListUsersPage users = FirebaseAuth.getInstance().listUsers(null);
        } catch (FirebaseAuthException e) {
            LoggerUtil.log(e.getMessage());
        }

    }

    @GetMapping("/user")
    public User getUser(String uid) {
        if (uid.isEmpty()) {
            LoggerUtil.log("UID was empty for getUser");
            return null;
        }
        try {
            UserRecord user = FirebaseAuth.getInstance().getUser(uid);
            //TODO return
            return null;
        } catch (FirebaseAuthException e) {
            LoggerUtil.log(e.getMessage());
            return null;
        }

    }

    @PatchMapping("/user")
    public void updateUser(User updatedUser) {
        //TODO var checks
        // return user?

        UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(updatedUser.uid())
                .setEmail(updatedUser.email())
                .setPhoneNumber(updatedUser.phoneNumber())
                .setEmailVerified(updatedUser.isEmailVerified())
                .setPassword(updatedUser.password())
                .setDisplayName(updatedUser.displayName())
                .setPhotoUrl(updatedUser.photoUrl())
                .setDisabled(updatedUser.isDisabled());

        try {
            UserRecord userRecord = FirebaseAuth.getInstance().updateUser(request);
        } catch (FirebaseAuthException e) {
            LoggerUtil.log(e.getMessage());
        }

    }

    @DeleteMapping("/user")
    public boolean deleteUser(String uid) {
        if (uid.isEmpty()) {
            LoggerUtil.log("UID was empty for deleteUser");
            return false;
        }
        try {
            FirebaseAuth.getInstance().deleteUser(uid);
        } catch (FirebaseAuthException e) {
            LoggerUtil.log(e.getMessage());
            return false;
        }
        return true;
    }

    @DeleteMapping("/tenant")
    public boolean deleteTenant(String tenantId) {
        //TODO if user are in tenant what happens?
        if (tenantId.isEmpty()) return false;
        TenantManager tenantManager = FirebaseAuth.getInstance().getTenantManager();
        try {
            tenantManager.deleteTenant(tenantId);
        } catch (FirebaseAuthException e) {
            LoggerUtil.log(e.getMessage());
            return false;
        }
        return true;
    }

    @PatchMapping("/tenant")
    public void updateTenant(String tenantId, String updatedTenantName) {
        if (tenantId.isEmpty()) return;
        TenantManager tenantManager = FirebaseAuth.getInstance().getTenantManager();
        Tenant.UpdateRequest request = new Tenant.UpdateRequest(tenantId)
                .setDisplayName(updatedTenantName)
                .setPasswordSignInAllowed(true)
                .setEmailLinkSignInEnabled(false);
        try {
            tenantManager.updateTenant(request);
        } catch (FirebaseAuthException e) {
            LoggerUtil.log(e.getMessage());
//            return false;
        }
    }

    @GetMapping("/tenant")
    public void getTenant(String tenantId) {
        TenantManager tenantManager = FirebaseAuth.getInstance().getTenantManager();

        try {
            tenantManager.getTenant(tenantId);
        } catch (FirebaseAuthException e) {
            LoggerUtil.log(e.getMessage());
//            return false;
        }
    }

    @GetMapping("/tenants")
    public void getAllTenants() {
        TenantManager tenantManager = FirebaseAuth.getInstance().getTenantManager();
        try {
            tenantManager.listTenants(null);
        } catch (FirebaseAuthException e) {
            LoggerUtil.log(e.getMessage());
//            return null;
        }
    }

    @PostMapping("/tenant")
    public void registerTenant(String tenantName) {
        // TODO return smth
        if (tenantName.isEmpty()) return;
        TenantManager tenantManager = FirebaseAuth.getInstance().getTenantManager();
        Tenant.CreateRequest request = new Tenant.CreateRequest()
                .setDisplayName(tenantName)
                .setEmailLinkSignInEnabled(false)
                .setPasswordSignInAllowed(true);
        try {
            tenantManager.createTenant(request);
        } catch (FirebaseAuthException e) {
            LoggerUtil.log(e.getMessage());
//            return false;
        }
    }

}
