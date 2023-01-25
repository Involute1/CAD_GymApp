package de.htwg.cadreportingservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String uid;
    private String email;
    private String displayName;
    private boolean isEmailVerified;
    private String tenantId;
}
