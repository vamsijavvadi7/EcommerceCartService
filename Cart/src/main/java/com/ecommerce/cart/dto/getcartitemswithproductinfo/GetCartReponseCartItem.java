package com.ecommerce.cart.dto.getcartitemswithproductinfo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
@Data
@RequiredArgsConstructor
public class GetCartReponseCartItem{

    private int cartitemid;
    private int productId;
    private String productname;
    private String productbrand;
    private String productcategory;
    private int productquantity;
    private String productdescription;
    private int productprice;
    private int cartitemprice;
    private int quantityAddedInCart;

}
