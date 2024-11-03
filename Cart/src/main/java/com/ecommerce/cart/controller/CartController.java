package com.ecommerce.cart.controller;

import com.ecommerce.cart.dto.getcartitemswithproductinfo.GetCartResponse;
import com.ecommerce.cart.dto.getcartitemswithproductinfo.Product;
import com.ecommerce.cart.feign.CartInterface;
import com.ecommerce.cart.feign.CartUserInterface;

import com.ecommerce.cart.model.User;
import com.ecommerce.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RequestMapping("cart")
@RestController
public class CartController {
  @Autowired
  CartService cartservice;

  @Autowired
  private CartUserInterface cartUserInterface;

  @Autowired
  private CartInterface cartInterface;




  //add cart
    @PostMapping("addOrRemoveProducttoCart/{userid}/{productid}/{quantity}")
    public ResponseEntity<?> addOrRemoveProducttoCart(@PathVariable int userid, @PathVariable int productid, @PathVariable int quantity) {
      Optional<User> user = cartUserInterface.getUserByID(userid);
      if (user.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("User ID not found: " + userid);
      }

      Optional<Product> product = cartInterface.getProductByID(productid);
      if (product.isEmpty() || Objects.isNull(product.get())) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Product ID not found: " + productid);
      }

// If both user and product are found, proceed to add or remove product from the cart
      return ResponseEntity.ok(cartservice.addOrRemoveProducttoCart(userid, productid, quantity));

    }




    //Delete Cart
    @DeleteMapping("deleteCart/{id}")
    public void deleteCart(@PathVariable int id) {
      cartservice.deleteCart(id);
    }

  //Delete CartItems of a card
  @DeleteMapping("clearCart/{cartid}")
  public void clearCart(@PathVariable int cartid) {
    cartservice.clearCart(cartid);
  }



    //get cart by id
    @GetMapping("getCart/{userid}")
    public ResponseEntity<Object> getCartByUserid(@PathVariable int userid) {
      return cartservice.getCartByUserid(userid);
    }


  /**
   * @param userid
   * @return totalcartprice
   */
  //get cart by id
  @GetMapping("getCartPrice/{userid}")
  public ResponseEntity<?> getCartPriceByUserid(@PathVariable int userid) {
    return cartservice.getCartPriceByUserid(userid);
  }






}
