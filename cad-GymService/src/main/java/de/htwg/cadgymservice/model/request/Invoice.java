package de.htwg.cadgymservice.model.request;

import lombok.*;

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
