package de.htwg.cadinvoicecronjob.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class User {
    private UUID uid;
    private String email;
    private String password;
    private String displayName;
    private String phoneNumber;
    private boolean isEmailVerified;
    private boolean isDisabled;
    private String photoUrl;
    private String tenantId;
}
