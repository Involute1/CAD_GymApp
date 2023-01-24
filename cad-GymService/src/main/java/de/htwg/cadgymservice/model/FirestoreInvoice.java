package de.htwg.cadgymservice.model;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FirestoreInvoice {
    @DocumentId
    private String firestoreId;
    private LocalDateTime BillingDate;
    private double Amount;
    private LocalDateTime DueDate;
}
