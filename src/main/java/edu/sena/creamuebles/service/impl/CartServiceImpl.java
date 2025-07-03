package edu.sena.creamuebles.service.impl;

import edu.sena.creamuebles.dto.CartItemRequestDTO;
import edu.sena.creamuebles.dto.CartItemResponseDTO;
import edu.sena.creamuebles.dto.CartResponseDTO;
import edu.sena.creamuebles.model.*;
import edu.sena.creamuebles.repository.CartRepository;
import edu.sena.creamuebles.repository.ProductRepository;
import edu.sena.creamuebles.service.CartService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public CartResponseDTO getCart(Optional<User> user, String sessionId) {
        Cart cart = findOrCreateCart(user, sessionId);
        return mapToCartResponseDTO(cart);
    }

    @Override
    @Transactional
    public CartResponseDTO addItemToCart(Optional<User> user, String sessionId, CartItemRequestDTO itemDTO) {
        Cart cart = findOrCreateCart(user, sessionId);
        Product product = productRepository.findById(itemDTO.productId())
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + itemDTO.productId()));

        if (product.getStock() < itemDTO.quantity()) {
            throw new IllegalStateException("No hay suficiente stock para el producto: " + product.getName());
        }

        CartItem existingItem = cart.findItemByProduct(product);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + itemDTO.quantity());
        } else {
            CartItem newItem = new CartItem(cart, product, itemDTO.quantity());
            cart.addItem(newItem);
        }

        cartRepository.save(cart);
        return mapToCartResponseDTO(cart);
    }

    // VERSIÓN CORREGIDA Y FUNCIONAL
    @Override
    @Transactional
    public CartResponseDTO removeItemFromCart(Optional<User> user, String sessionId, Long productId) {
        Cart cart = findOrCreateCart(user, sessionId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + productId));

        // --- INICIO DE LA CORRECCIÓN ---
        // 1. Primero, busca el 'CartItem' que contiene el producto.
        //    Este método ya lo usas en otras partes, así que debería existir en tu clase Cart.
        CartItem itemToRemove = cart.findItemByProduct(product);

        // 2. Si se encontró el item, elimínalo del carrito.
        //    Este método también lo usas en 'decreaseItem'.
        if (itemToRemove != null) {
            cart.removeItem(itemToRemove);
        }
        // --- FIN DE LA CORRECCIÓN ---

        cartRepository.save(cart);
        return mapToCartResponseDTO(cart);
    }

    @Override
    @Transactional
    public void clearCart(Optional<User> user, String sessionId) {
        Cart cart = findOrCreateCart(user, sessionId);
        cart.clear();
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public CartResponseDTO decreaseItem(Optional<User> user, String sessionId, Long productId) {
        Cart cart = findOrCreateCart(user, sessionId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + productId));

        CartItem item = cart.findItemByProduct(product);
        if (item != null) {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
            } else {
                cart.removeItem(item);
            }
        }
        cartRepository.save(cart);
        return mapToCartResponseDTO(cart);
    }

    @Override
    @Transactional
    public CartResponseDTO increaseItem(Optional<User> user, String sessionId, Long productId) {
        // Aumentar es lo mismo que añadir 1 item
        return addItemToCart(user, sessionId, new CartItemRequestDTO(productId, 1));
    }

    @Override
    public int getItemCount(Optional<User> user, String sessionId) {
        Cart cart = findOrCreateCart(user, sessionId);
        return cart.getTotalItems();
    }

    // --- Métodos Privados de Ayuda ---

    private Cart findOrCreateCart(Optional<User> user, String sessionId) {
        Optional<Cart> cartOpt;
        if (user.isPresent()) {
            cartOpt = cartRepository.findFirstByUserIdAndStatus(user.get().getId(), Cart.CartStatus.ACTIVE);
            if (cartOpt.isEmpty()) {
                return cartRepository.save(new Cart(user.get()));
            }
        } else {
            cartOpt = cartRepository.findFirstBySessionIdAndStatus(sessionId, Cart.CartStatus.ACTIVE);
            if (cartOpt.isEmpty()) {
                return cartRepository.save(new Cart(sessionId));
            }
        }
        return cartOpt.get();
    }

    private CartResponseDTO mapToCartResponseDTO(Cart cart) {
        if (cart == null) {
            return new CartResponseDTO(null, 0, 0, java.math.BigDecimal.ZERO, Collections.emptyList());
        }
        List<CartItemResponseDTO> itemDTOs = cart.getItems().stream()
                .map(this::mapToCartItemResponseDTO)
                .collect(Collectors.toList());

        return new CartResponseDTO(
                cart.getId(),
                cart.getTotalItems(),
                cart.getTotalProducts(),
                cart.getTotalAmount(),
                itemDTOs
        );
    }

    private CartItemResponseDTO mapToCartItemResponseDTO(CartItem item) {
        Product product = item.getProduct();
        return new CartItemResponseDTO(
                item.getId(),
                product.getId(),
                product.getName(),
                product.getImageUrl(),
                product.getEffectivePrice(),
                item.getQuantity(),
                item.getSubtotal()
        );
    }
}