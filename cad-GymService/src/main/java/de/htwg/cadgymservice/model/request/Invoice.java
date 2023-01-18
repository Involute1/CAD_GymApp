package de.htwg.cadgymservice.model.request;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@Data
public class Invoice implements Serializable {
    private String firebaseId;
    private Date BillingDate;
    private double Amount;
    private Date DueDate;
    private String GymId;
}
