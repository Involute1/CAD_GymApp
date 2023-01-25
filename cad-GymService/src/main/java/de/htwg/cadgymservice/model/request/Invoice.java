package de.htwg.cadgymservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class Invoice implements Serializable {
    private String firestoreId;
    private LocalDateTime billingDate;
    private double amount;
    private LocalDateTime dueDate;
    private String gymId;
}
