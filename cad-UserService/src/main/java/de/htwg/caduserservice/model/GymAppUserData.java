package de.htwg.caduserservice.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "GYMAPPUSERDATA")
@Data
public class GymAppUserData {

    public GymAppUserData() {

    }

    public GymAppUserData(String uid, Roles role) {
        this.uid = uid;
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "UID", nullable = false)
    private String uid;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false)
    private Roles role;
}
