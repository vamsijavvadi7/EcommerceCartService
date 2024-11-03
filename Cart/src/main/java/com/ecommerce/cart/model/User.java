package com.ecommerce.cart.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Entity
@Component
@Data
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String password;
    private String username;
    private String email;

    @Embedded
    private Address address;

    @Embeddable
    @Data
    @RequiredArgsConstructor
    public static class Address {
        private String streetAddress;
        private String city;
        private String state;
        private String country;


    }

}
