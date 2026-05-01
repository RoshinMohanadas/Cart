package com.roshin.cart;

import com.roshin.cart.dto.Cart;
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
    public ResponseEntity<Cart> createCart(@PathVariable final String user)
    {
        return ResponseEntity.ok(cartService.createCart(user));
    }

    // Add to cart
    @PutMapping("add/{cartId}/{productId}")
    public ResponseEntity<Cart> addProductToCart(@PathVariable final Long cartId, @PathVariable final Long productId)
    {
        return ResponseEntity.ok(cartService.addProductToCart(cartId, productId));
    }

    // Remove from cart
    @PutMapping("delete/{cartId}/{productId}")
    public ResponseEntity<Cart> deleteProductFromCart(@PathVariable final Long cartId, @PathVariable final Long productId)
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
