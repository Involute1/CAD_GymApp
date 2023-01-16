package de.htwg.caduserservice.model;

import java.util.UUID;

public record UserData(UUID uid, String email, String password, String displayName, String phoneNumber,
                       boolean isEmailVerified, boolean isDisabled, String photoUrl, String tenantId) {
}
