package de.htwg.cadinvoicecronjob.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@Data
public class Invoice implements Serializable {
    private String firebaseId;
    private LocalDateTime BillingDate;
    private double Amount;
    private LocalDateTime DueDate;
    private String GymId;
}
