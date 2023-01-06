package de.htwg.cadgymservice.model;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Gym implements Serializable {
    @DocumentId
    private String id;
    private String name;
    private String tenantId;
    private String description;
    private byte[] logo;

    public Gym(String name, String tenantId, String description) {
        this.name = name;
        this.tenantId = tenantId;
        this.description = description;
    }
}
