package de.htwg.cadworkoutservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private String uid;
    private String email;
    private String password;
    private String displayName;
    private String phoneNumber;
    private boolean isEmailVerified;
    private boolean isDisabled;
    private String photoUrl;
    private String tenantId;
}
