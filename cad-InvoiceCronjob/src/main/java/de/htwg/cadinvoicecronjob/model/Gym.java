package de.htwg.cadinvoicecronjob.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Gym implements Serializable {
    private String firebaseId;
    private String name;
    private String tenantId;
    private String description;
    private BillingModel billingModel;
    private byte[] logo;

}
