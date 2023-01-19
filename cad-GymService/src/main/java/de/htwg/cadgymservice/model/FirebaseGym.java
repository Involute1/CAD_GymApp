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
public class FirebaseGym implements Serializable {
    @DocumentId
    private String firebaseId;
    private String name;
    private String tenantId;
    private String description;
    private BillingModel billingModel;
    private List<FirebaseInvoice> invoices;
}
