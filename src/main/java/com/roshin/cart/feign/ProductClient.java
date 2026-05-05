package com.roshin.cart.feign;

import com.roshin.cart.dto.ProductInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("PRODUCT-SERVICE")
public interface ProductClient {
    @PostMapping("/demo/specific-products")
    public List<ProductInfo> getSpecificProducts(@RequestBody final List<Long> productIds);
}
