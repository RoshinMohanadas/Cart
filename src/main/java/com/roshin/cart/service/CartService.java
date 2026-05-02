package com.roshin.cart.service;

import com.roshin.cart.dto.Cart;
import com.roshin.cart.dto.ProductInfo;
import com.roshin.cart.persistence.CartPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService
{
    CartPersistence cartPersistence;

    @Autowired
    public CartService(CartPersistence cartPersistence)
    {
        this.cartPersistence = cartPersistence;
    }

    public Cart createCart(final String user)
    {
        Cart newCart = Cart.builder()
                .user(user).build();
        return cartPersistence.createCart(newCart);
    }

    public Cart getUpdatedCart(final Long id)
    {
        // Get the updated products using API call

        return cartPersistence.getCart(id);
    }

    public Cart addProductToCart(final Long cartId, final ProductInfo product)
    {
        // User based validation
        return cartPersistence.addProductToCart(cartId, product);
    }

    public Cart setCount(final Long cartId,
                              final Long productId,
                              final Long newCount)
    {
        return cartPersistence.setCount(cartId, productId, newCount);
    }

    public Cart deleteProductFromCart(final Long cartId, final Long productId)
    {
        return cartPersistence.deleteProductFromCart(cartId, productId);
    }
}
