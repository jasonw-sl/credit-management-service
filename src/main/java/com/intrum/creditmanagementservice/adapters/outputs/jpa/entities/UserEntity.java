package com.intrum.creditmanagementservice.adapters.outputs.jpa.entities;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "USERS_T")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "CUSTOMER_ID", nullable = false, unique = true)
    private Long customerId;

    @Column(name = "USERNAME", length = 50, nullable = false, unique = true)
    private String username;

    @Column(name = "PASSWORD_HASH", length = 50, nullable = false)
    private String passwordHash;
}
