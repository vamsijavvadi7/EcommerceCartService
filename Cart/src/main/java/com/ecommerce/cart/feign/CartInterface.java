package com.ecommerce.cart.feign;


import com.ecommerce.cart.dto.getcartitemswithproductinfo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient("product")
public interface CartInterface {
    //Servlet to get product ny id
    @GetMapping("/product/getProduct/{id}")
    public Optional<Product> getProductByID(@PathVariable int id);
}
