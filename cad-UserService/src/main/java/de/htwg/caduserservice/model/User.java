package de.htwg.caduserservice.model;

public record User(String uid, String email, String password, String displayName, String phoneNumber,
                   boolean isEmailVerified, boolean isDisabled, String photoUrl) {
}
