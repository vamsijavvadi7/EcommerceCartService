package com.ecommerce.cart.dto.getcartitemswithproductinfo;


import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    String brand;
    @Lob
    private byte[] productImage; // field for storing image as binary data
    Category category; // Change category from String to Category
    int quantity;
    String description;
    int price;

    @Data
    @RequiredArgsConstructor
    public class Category {
        int id;
        String categoryname;
        String description;
    }

}
