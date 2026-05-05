package com.roshin.cart.service;

import com.roshin.cart.dto.Cart;
import com.roshin.cart.dto.ProductInfo;
import com.roshin.cart.dto.ProductLine;
import com.roshin.cart.feign.ProductClient;
import com.roshin.cart.persistence.CartPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CartService
{
    CartPersistence cartPersistence;

    ProductClient productClient;

    @Autowired
    public CartService(CartPersistence cartPersistence, ProductClient productClient)
    {
        this.cartPersistence = cartPersistence;
        this.productClient = productClient;
    }

    public Cart createCart(final String user)
    {
        Cart newCart = Cart.builder()
                .user(user).build();
        return cartPersistence.createCart(newCart);
    }

    public Cart updateCart(final Long id)
    {
        // Get the products within the cart
        Cart cart = cartPersistence.getCart(id);

        List<Long> productIds = cart.getProductLines().stream().map(ProductLine::getProductId).toList();

        // Get the updated products using API call
        final List<ProductInfo> products = productClient.getSpecificProducts(productIds);

        final Map<Long, ProductInfo> productMap = products.stream().collect(Collectors.toMap(ProductInfo::getId, Function.identity()));

        // Persist cart
        return cartPersistence.updateCartPrices(cart.getId(), productMap);
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


    public Cart getCart(Long cartId)
    {
        // Get
        return cartPersistence.getCart(cartId);
    }
}
