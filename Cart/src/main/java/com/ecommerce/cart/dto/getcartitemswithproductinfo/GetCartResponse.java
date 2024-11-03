package com.ecommerce.cart.dto.getcartitemswithproductinfo;

import com.ecommerce.cart.model.Cart;
import com.ecommerce.cart.model.CartItem;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class GetCartResponse {

    private int id;
    private int userid;
    private int totalCartPrice;
    private List<GetCartReponseCartItem> cartitems=new ArrayList<GetCartReponseCartItem>();
}
