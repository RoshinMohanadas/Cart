package com.roshin.cart.persistence;

import com.roshin.cart.dto.Cart;
import com.roshin.cart.model.CartEntity;
import com.roshin.cart.model.ProductLineEntity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public class CartPersistence
{
    CartRepository cartRepository;

    EntityManager entityManager;

    @Autowired
    public CartPersistence(CartRepository cartRepository, EntityManager entityManager)
    {
        this.cartRepository = cartRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public Cart createCart(final Cart cart)
    {
        final CartEntity newCart = new CartEntity();

        newCart.setUser(cart.getUser());
        newCart.setProductLines(new ArrayList<>());

        entityManager.persist(newCart);
        entityManager.flush();
        cart.setId(newCart.getId());
        return cart;
    }

    @Transactional
    public void addProductToCart(final Long cartId, final Long productId)
    {
        // Find existing cart
        final Optional<CartEntity> existingCart = cartRepository.findById(cartId);

        if(existingCart.isPresent())
        {
            existingCart.get().getProductLines().addFirst(ProductLineEntity.builder()
                            .productId(productId)
                            .count(1L)
                    .build());
        }
        else
        {
            throw new NotFoundException("Cart not found");
        }
    }

    @Transactional
    public void deleteProductFromCart(final Long cartId, final Long productId)
    {
        // Find existing cart
        final Optional<CartEntity> existingCart = cartRepository.findById(cartId);

        if(existingCart.isPresent())
        {
            if(!existingCart.get().getProductLines().remove(ProductLineEntity.builder()
                    .id(productId).build()))
            {
                throw new NotFoundException("Product not found");
            }
        }
        else
        {
            throw new NotFoundException("Cart not found");
        }
    }

    public void increaseCount(
            final Long cartId,
            final Long productId)
    {
        // Find existing cart
        final Optional<CartEntity> existingCart = cartRepository.findById(cartId);

        if(existingCart.isPresent())
        {
            // Find the product
            Optional<ProductLineEntity> productLineEntity = existingCart.get().getProductLines().stream()
                    .filter(p -> p.getProductId().equals(productId)).findFirst();

            if(productLineEntity.isPresent())
            {
                final ProductLineEntity productMatch = productLineEntity.get();
                productMatch.setCount(productMatch.getCount() + 1);
            }
            else
            {
                throw new NotFoundException("Product not found");
            }
        }
        else
        {
            throw new NotFoundException("Cart not found");
        }
    }

    @Transactional
    public void reduceCount(
            final Long cartId,
            final Long productId)
    {
        // Find existing cart
        final Optional<CartEntity> existingCart = cartRepository.findById(cartId);

        if(existingCart.isPresent())
        {
            // Find the product
            Optional<ProductLineEntity> productLineEntity = existingCart.get().getProductLines().stream()
                    .filter(p -> p.getProductId().equals(productId)).findFirst();

            if(productLineEntity.isPresent())
            {
                final ProductLineEntity productMatch = productLineEntity.get();
                Long updatedCount = productMatch.getCount() > 0 ? productMatch.getCount() - 1: 0;
                productMatch.setCount(updatedCount);
            }
            else
            {
                throw new NotFoundException("Product not found");
            }
        }
        else
        {
            throw new NotFoundException("Cart not found");
        }
    }
}
