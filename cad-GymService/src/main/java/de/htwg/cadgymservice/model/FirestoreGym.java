package de.htwg.cadgymservice.model;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FirestoreGym implements Serializable {
    @DocumentId
    private String id;
    private String name;
    private String tenantId;
    private String description;
    private BillingModel billingModel;
    private List<FirestoreInvoice> invoices;

    public FirestoreGym(String name, String description, BillingModel billingModel) {
        this.name = name;
        this.description = description;
        this.billingModel = billingModel;
    }
}
