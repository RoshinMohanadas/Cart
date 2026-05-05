package com.roshin.cart;

import com.roshin.cart.dto.Cart;
import com.roshin.cart.dto.ProductInfo;
import com.roshin.cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController
{
    CartService cartService;

    public CartController(CartService cartService)
    {
        this.cartService = cartService;
    }

    // Create cart
    @PostMapping("/{user}")
    public ResponseEntity<Cart> createCart(@PathVariable("user") final String user)
    {
        return ResponseEntity.ok(cartService.createCart(user));
    }

    @PostMapping("/refresh/{id}")
    public ResponseEntity<Cart> refreshCart(@PathVariable("id") final Long cartId)
    {
        return ResponseEntity.ok(cartService.updateCart(cartId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCart(@PathVariable("id") final Long cartId)
    {
        return ResponseEntity.ok(cartService.getCart(cartId));
    }

    // Add to cart
    @PostMapping("add/{cartId}")
    public ResponseEntity<Cart> addProductToCart(
            @PathVariable("cartId") final Long cartId,
            @RequestBody final ProductInfo product)
    {
        return ResponseEntity.ok(cartService.addProductToCart(cartId, product));
    }

    // Remove from cart
    @PostMapping("delete/{cartId}/{productId}")
    public ResponseEntity<Cart> deleteProductFromCart(
            @PathVariable("cartId") final Long cartId,
            @PathVariable("productId") final Long productId)
    {
        return ResponseEntity.ok(cartService.deleteProductFromCart(cartId, productId));
    }

    // Set product count
    @PutMapping("setCount/{cartId}/{productId}/{count}")
    public ResponseEntity<Cart> setProductCount(
            @PathVariable("cartId") final Long cartId,
            @PathVariable("productId") final Long productId,
            @PathVariable("count") final Long count)
    {
        return ResponseEntity.ok(cartService.setCount(cartId, productId, count));
    }

    // Empty cart

    // Generate summary
}
