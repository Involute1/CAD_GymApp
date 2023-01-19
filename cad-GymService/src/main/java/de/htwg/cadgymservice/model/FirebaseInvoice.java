package de.htwg.cadgymservice.model;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class FirebaseInvoice {
    @DocumentId
    private String firebaseId;
    private LocalDateTime BillingDate;
    private double Amount;
    private LocalDateTime DueDate;
}
