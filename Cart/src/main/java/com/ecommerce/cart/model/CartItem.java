package com.ecommerce.cart.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
public class CartItem{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int id;
     private int productId;
     private int price;
     private int quantityAddedInCart;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")// Foreign key in CartItem table
    @JsonBackReference
    private Cart cart;
}
