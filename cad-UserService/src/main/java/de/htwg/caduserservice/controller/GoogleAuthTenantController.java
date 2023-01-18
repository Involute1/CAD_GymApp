package de.htwg.caduserservice.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.multitenancy.ListTenantsPage;
import com.google.firebase.auth.multitenancy.Tenant;
import com.google.firebase.auth.multitenancy.TenantManager;
import de.htwg.caduserservice.model.TenantData;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tenant")
public class GoogleAuthTenantController {
    private static final Log LOGGER = LogFactory.getLog(GoogleAuthTenantController.class);

    @DeleteMapping("/{tenantId}")
    public boolean deleteTenant(@PathVariable String tenantId) {
        //TODO what happens with the users that are in there?
        if (StringUtils.isBlank(tenantId)) {
            LOGGER.error("tenantId was empty or null on deleteTenant");
            return false;
        }
        TenantManager tenantManager = FirebaseAuth.getInstance().getTenantManager();
        try {
            tenantManager.deleteTenant(tenantId);
            return true;
        } catch (FirebaseAuthException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    @PatchMapping("/{tenantId}")
    public TenantData updateTenant(@PathVariable String tenantId, String updatedTenantName) {
        if (StringUtils.isBlank(tenantId) || StringUtils.isBlank(updatedTenantName)) {
            LOGGER.error("tenantId || updatedTenantName was empty or null on updateTenant");
            return null;
        }
        TenantManager tenantManager = FirebaseAuth.getInstance().getTenantManager();
        Tenant.UpdateRequest request = new Tenant.UpdateRequest(tenantId)
                .setDisplayName(updatedTenantName)
                .setPasswordSignInAllowed(true)
                .setEmailLinkSignInEnabled(false);
        try {
            Tenant updatedTenant = tenantManager.updateTenant(request);
            return new TenantData(UUID.fromString(updatedTenant.getTenantId()), updatedTenant.getDisplayName());
        } catch (FirebaseAuthException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    @GetMapping("/{tenantId}")
    public TenantData getTenant(@PathVariable String tenantId) {
        if (StringUtils.isBlank(tenantId)) {
            LOGGER.error("tenantId was empty or null on getTenant");
            return null;
        }
        TenantManager tenantManager = FirebaseAuth.getInstance().getTenantManager();
        try {
            Tenant tenant = tenantManager.getTenant(tenantId);
            return new TenantData(UUID.fromString(tenant.getTenantId()), tenant.getDisplayName());
        } catch (FirebaseAuthException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    @GetMapping("/all")
    public List<TenantData> getAllTenants() {
        TenantManager tenantManager = FirebaseAuth.getInstance().getTenantManager();
        try {
            List<TenantData> tenantDataList = new ArrayList<>();
            ListTenantsPage tenants = tenantManager.listTenants(null);
            tenants.iterateAll().forEach(e -> tenantDataList.add(new TenantData(UUID.fromString(e.getTenantId()), e.getDisplayName())));
            return tenantDataList;
        } catch (FirebaseAuthException e) {
            LOGGER.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    @PostMapping("/register")
    public TenantData registerTenant(String tenantName) {
        if (StringUtils.isBlank(tenantName)) {
            LOGGER.error("tenantName was empty or null on registerTenant");
            return null;
        }
        TenantManager tenantManager = FirebaseAuth.getInstance().getTenantManager();
        Tenant.CreateRequest request = new Tenant.CreateRequest()
                .setDisplayName(tenantName)
                .setEmailLinkSignInEnabled(false)
                .setPasswordSignInAllowed(true);
        try {
            Tenant createdTenant = tenantManager.createTenant(request);
            return new TenantData(UUID.fromString(createdTenant.getTenantId()), createdTenant.getDisplayName());
        } catch (FirebaseAuthException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }
}
