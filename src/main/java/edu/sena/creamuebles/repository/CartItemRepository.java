package edu.sena.creamuebles.repository;

import edu.sena.creamuebles.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // Encontrar todos los ítems de un carrito específico
    List<CartItem> findByCartId(Long cartId);

    // Encontrar todos los ítems que contienen un producto específico
    // (útil para saber en cuántos carritos está un producto)
    List<CartItem> findByProductId(Long productId);
}