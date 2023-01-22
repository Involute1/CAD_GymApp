package de.htwg.caduserservice.model;

public record UserData(Roles role, String uid, String email, String displayName,
                       boolean isEmailVerified, String tenantId) {
}
