package com.ecommerce.cart.service;


import com.ecommerce.cart.dao.CartItemRepo;
import com.ecommerce.cart.dao.CartRepo;
import com.ecommerce.cart.dto.getcartitemswithproductinfo.GetCartReponseCartItem;
import com.ecommerce.cart.dto.getcartitemswithproductinfo.GetCartResponse;
import com.ecommerce.cart.dto.getcartitemswithproductinfo.Product;
import com.ecommerce.cart.feign.CartInterface;
import com.ecommerce.cart.model.Cart;
import com.ecommerce.cart.model.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class CartService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private CartInterface cartInterface;




    // Set the quantity directly from the front end
    public Cart addOrRemoveProducttoCart(int userid, int productid, int quantity) {


            // Retrieve cart for the user
            Cart cart = cartRepo.findByUserid(userid);

            // If the cart does not exist, create a new one
            if (Objects.isNull(cart)) {
                cart = new Cart();
                cart.setUserid(userid);
            }

            // Check if the product is already in the cart
            CartItem cartItem = cart.getCartitems().stream()
                    .filter(item -> item.getProductId() == productid)
                    .findFirst()
                    .orElse(null);

            boolean isCartItemnew=false;
            // If the item is not in the cart, add it
            if (Objects.isNull(cartItem)) {
                // Ensure that the quantity to be set is valid
                if (quantity <= 0) {
                    throw new IllegalArgumentException("Quantity must be greater than zero when adding a new item.");
                }
                cartItem = new CartItem();
                cartItem.setProductId(productid);
                cartItem.setQuantityAddedInCart(quantity);
                isCartItemnew=true;

            } else {
                // If the item exists, update the quantity directly
                if (quantity <= 0) {
                    // If the quantity is 0 or less, remove the item from the cart
                    cart.removeCartItem(cartItem);
                } else {
                    // Otherwise, set the new quantity directly
                    cartItem.setQuantityAddedInCart(quantity);
                }
            }
            //get product form product service
            Optional<Product> productFromProductService = cartInterface.getProductByID(productid);
            //if prresent
            if (productFromProductService.isPresent()) {
                Product product = productFromProductService.get();
                cartItem.setPrice(product.getPrice());
                  //check the stock of product
                if(cartItem.getQuantityAddedInCart()<=product.getQuantity()){
                    if(isCartItemnew)
                    cart.addCartItem(cartItem); // Add the item to the cart if in stock and if it is not yet added to cart

                    // If cart total price is zero, set it to the current cart item's price * quantity
                    if (cart.getTotalCartPrice() == 0) {
                        cart.setTotalCartPrice(cartItem.getPrice() * cartItem.getQuantityAddedInCart());
                    } else {
                        // Otherwise, calculate the total price by summing all items' (price * quantity)
                        double totalPrice = cart.getCartitems().stream()
                                .mapToDouble(item -> item.getPrice() * item.getQuantityAddedInCart())
                                .sum();
                        cart.setTotalCartPrice((int) totalPrice);
                    }
              }
            }
            // Save the updated cart
            return cartRepo.save(cart);
    }

    // Delete Cart
    public void deleteCart(int cartid) {
        cartRepo.deleteById(cartid);
    }

    // Get all carts
    public List<Cart> getCarts() {
        return cartRepo.findAll();
    }

    // Get cart by ID
    public ResponseEntity<Object> getCartByUserid(int userid) {

            Optional<Cart> cartOptional = Optional.ofNullable(cartRepo.findByUserid(userid));

        GetCartResponse getCartResponse=new GetCartResponse();
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            getCartResponse.setId(cart.getId());
            getCartResponse.setUserid(cart.getUserid());

            List<CartItem> cartItems = cart.getCartitems();
            //GetCartReponseCartItem Dto Creation
            List<GetCartReponseCartItem> getCartReponseCartItems=new ArrayList<GetCartReponseCartItem>();

            for (CartItem item : cartItems) {
                GetCartReponseCartItem getCartReponseCartItem=new GetCartReponseCartItem();
                getCartReponseCartItem.setCartitemid(item.getId());
                getCartReponseCartItem.setProductId(item.getProductId());

//getting and setting product info
                Optional<Product> productFromProductService=cartInterface.getProductByID(item.getProductId());

                if (productFromProductService.isPresent()) {
                    Product product = productFromProductService.get();
                    getCartReponseCartItem.setProductbrand(product.getBrand());
                    getCartReponseCartItem.setProductcategory(product.getCategory().getCategoryname());
                    getCartReponseCartItem.setProductname(product.getName());
                    getCartReponseCartItem.setProductdescription((product.getDescription()));
                    getCartReponseCartItem.setProductquantity(product.getQuantity());
                    getCartReponseCartItem.setProductprice(product.getPrice());
                    getCartReponseCartItem.setCartitemprice(product.getPrice());
                }


                getCartReponseCartItem.setQuantityAddedInCart(item.getQuantityAddedInCart());

                getCartReponseCartItems.add(getCartReponseCartItem);

            }
//setting cartitems and totalcartprice
            getCartResponse.setCartitems(getCartReponseCartItems);
            getCartResponse.setTotalCartPrice(cart.getTotalCartPrice());
            return  ResponseEntity.status(HttpStatus.OK).body(getCartResponse);
        } else {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + userid);
        }




    }

    public ResponseEntity<?> getCartPriceByUserid(int userid) {
        Optional<Cart> cartOptional = Optional.ofNullable(cartRepo.findByUserid(userid));
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
        return  ResponseEntity.status(HttpStatus.OK).body(cart.getTotalCartPrice());
    } else {
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found for user with ID: " + userid);
    }
    }

    public void clearCart(int cartid) {
        cartItemRepo.deleteByCart_id(cartid);
    }

}
