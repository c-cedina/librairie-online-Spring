package com.example.librairie_online.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Validation {
    @Id
    private int id;
    private int code;
    @Column(name = "Creation_instant")
    private Instant creation;
    @Column(name = "Expire_instant")
    private Instant expire;
    @Column(name = "Activation_instant")
    private Instant activation;
    @OneToOne
    @JoinColumn(name = "ClientId",referencedColumnName = "NAdherent")
    private  Client client;
}
