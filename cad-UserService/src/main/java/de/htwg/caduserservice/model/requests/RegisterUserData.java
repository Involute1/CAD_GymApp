package de.htwg.caduserservice.model.requests;

import de.htwg.caduserservice.model.BillingModel;
import de.htwg.caduserservice.model.Roles;

public record RegisterUserData(Roles role, String email, String password, String displayName,
                               String tenantId, String gymName, BillingModel billingModel) {
}
