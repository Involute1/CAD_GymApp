package de.htwg.caduserservice.model;

public record RegisterUserData(Roles role, String uid, String email, String password, String displayName,
                               boolean isEmailVerified, String tenantId, String gymName, String billingModel) {
}
