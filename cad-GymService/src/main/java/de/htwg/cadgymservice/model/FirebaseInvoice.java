package de.htwg.cadgymservice.model;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class FirebaseInvoice {
    @DocumentId
    private String firebaseId;
    private Date BillingDate;
    private double Amount;
    private Date DueDate;
}
