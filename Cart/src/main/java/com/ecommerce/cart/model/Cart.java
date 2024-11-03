package com.ecommerce.cart.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
public class Cart{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userid;
    private int totalCartPrice;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CartItem> cartitems=new ArrayList<CartItem>();

    // Helper method to add cart items
    public void addCartItem(CartItem item) {
        cartitems.add(item);
        item.setCart(this);
    }

    // Helper method to remove cart items
    public void removeCartItem(CartItem item) {
        cartitems.remove(item);
        item.setCart(null);
    }
}
