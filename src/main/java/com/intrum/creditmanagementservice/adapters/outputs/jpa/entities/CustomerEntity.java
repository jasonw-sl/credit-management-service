package com.intrum.creditmanagementservice.adapters.outputs.jpa.entities;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "CUSTOMERS_T")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    @Column(name = "SURNAME", length = 50, nullable = false)
    private String surname;

    @Column(name = "CODE", length = 20, nullable = false)
    private String code;

    @Column(name = "COUNTRY", length = 2, nullable = false)
    private String country;

    @Column(name = "EMAIL", length = 50, nullable = false)
    private String email;
}
