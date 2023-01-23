package de.htwg.caduserservice.model;

public record RegisterUserData(Roles role, String email, String password, String displayName,
                               String tenantId, String gymName, String billingModel) {
}
