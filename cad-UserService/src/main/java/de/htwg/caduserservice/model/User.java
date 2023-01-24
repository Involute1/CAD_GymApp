package de.htwg.caduserservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "GYM_USER")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;
    @Column(name = "UID", nullable = false)
    private String uid;
    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false)
    private Roles role;

    public User() {
    }

    public User(String uid, Roles role) {
        this.uid = uid;
        this.role = role;
    }
}
