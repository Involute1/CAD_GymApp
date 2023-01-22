package de.htwg.caduserservice.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "USER")
@Data
public class User {

    public User() {

    }

    public User(String uid, Roles role) {
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
