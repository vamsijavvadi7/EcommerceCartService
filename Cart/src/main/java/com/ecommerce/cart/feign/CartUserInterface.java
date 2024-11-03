package com.ecommerce.cart.feign;


import com.ecommerce.cart.dto.getcartitemswithproductinfo.Product;
import com.ecommerce.cart.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient("users")
public interface CartUserInterface {
    @GetMapping("/user/getUser/{id}")
    public Optional<User> getUserByID(@PathVariable int id);
}
