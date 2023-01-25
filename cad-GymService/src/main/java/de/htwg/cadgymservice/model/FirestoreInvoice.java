package de.htwg.cadgymservice.model;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FirestoreInvoice {
    @DocumentId
    private String firestoreId;
    private Long billingDate;
    private double amount;
    private Long dueDate;
}
