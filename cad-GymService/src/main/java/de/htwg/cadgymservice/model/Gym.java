package de.htwg.cadgymservice.model;

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
    private String firebaseId;
    private String name;
    private String tenantId;
    private String description;
    private byte[] logo;

}