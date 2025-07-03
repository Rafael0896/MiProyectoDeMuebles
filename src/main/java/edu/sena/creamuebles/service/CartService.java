package edu.sena.creamuebles.service;

import edu.sena.creamuebles.dto.CartItemRequestDTO;
import edu.sena.creamuebles.dto.CartResponseDTO;
import edu.sena.creamuebles.model.User;

import java.util.Optional;

public interface CartService {

    CartResponseDTO getCart(Optional<User> user, String sessionId);

    CartResponseDTO addItemToCart(Optional<User> user, String sessionId, CartItemRequestDTO itemDTO);

    CartResponseDTO removeItemFromCart(Optional<User> user, String sessionId, Long productId);

    void clearCart(Optional<User> user, String sessionId);

    /**
     * Disminuye en 1 la cantidad de un item en el carrito.
     * Si la cantidad llega a 0, el item se elimina.
     */
    CartResponseDTO decreaseItem(Optional<User> user, String sessionId, Long productId);

    /**
     * Aumenta en 1 la cantidad de un item en el carrito.
     */
    CartResponseDTO increaseItem(Optional<User> user, String sessionId, Long productId);

    /**
     * Obtiene el número total de productos en el carrito.
     */
    int getItemCount(Optional<User> user, String sessionId);
}