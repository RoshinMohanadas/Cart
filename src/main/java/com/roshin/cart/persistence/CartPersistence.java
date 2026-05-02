package com.roshin.cart.persistence;

import com.roshin.cart.dto.Cart;
import com.roshin.cart.dto.ProductInfo;
import com.roshin.cart.model.CartEntity;
import com.roshin.cart.model.ProductLineEntity;
import com.roshin.cart.util.Mapper;
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
        // Check if user has cart already
        Optional<CartEntity> userCart = cartRepository.findByUsername(cart.getUser());

        if(userCart.isPresent())
        {
            throw new IllegalStateException("Cart already existing for user: " + cart.getUser());
        }

        final CartEntity newCart = new CartEntity();

        newCart.setUsername(cart.getUser());
        newCart.setProductLines(new ArrayList<>());

        entityManager.persist(newCart);
        entityManager.flush();
        cart.setId(newCart.getId());
        return cart;
    }

    @Transactional
    public Cart addProductToCart(final Long cartId, final ProductInfo product)
    {
        // Find existing cart
        final Optional<CartEntity> existingCart = cartRepository.findById(cartId);

        if(existingCart.isPresent())
        {
            // Check if product already exists in cart
            if(!existingCart.get().getProductLines().stream().anyMatch(p -> p.getProductId().equals(product.getId())))
            {
                existingCart.get().getProductLines().addFirst(ProductLineEntity.builder()
                        .productId(product.getId())
                        .productCost(product.getPrice())
                        .count(1L)
                        .build());
            }

            return Mapper.convertEntityToDTO(existingCart.get());
        }
        else
        {
            throw new NotFoundException("Cart not found");
        }
    }

    public Cart getCart(final Long id)
    {
        // Find existing cart
        final Optional<CartEntity> existingCart = cartRepository.findById(id);

        if(existingCart.isPresent())
        {
            return Mapper.convertEntityToDTO(existingCart.get());
        }
        else
        {
            throw new NotFoundException("Cart not found");
        }
    }

    @Transactional
    public Cart deleteProductFromCart(final Long cartId, final Long productId)
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
            else
            {
                return Mapper.convertEntityToDTO(existingCart.get());
            }
        }
        else
        {
            throw new NotFoundException("Cart not found");
        }
    }

    @Transactional
    public Cart setCount(
            final Long cartId,
            final Long productId,
            final Long newCount)
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
                productMatch.setCount(newCount >= 0 ? newCount: 0);

                return Mapper.convertEntityToDTO(existingCart.get());
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
