package de.htwg.caduserservice.model;

public record UserData(String uid, String email, String password, String displayName, String phoneNumber,
                       boolean isEmailVerified, boolean isDisabled, String photoUrl, String tenantId) {
}
