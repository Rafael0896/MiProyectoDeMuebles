package edu.sena.creamuebles.repository;

import edu.sena.creamuebles.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    /**
     * Busca el primer carrito activo asociado a un ID de usuario.
     * Se usa para usuarios que han iniciado sesión.
     */
    Optional<Cart> findFirstByUserIdAndStatus(Long userId, Cart.CartStatus status);

    /**
     * Busca el primer carrito activo asociado a un ID de sesión.
     * Se usa para usuarios anónimos.
     */
    Optional<Cart> findFirstBySessionIdAndStatus(String sessionId, Cart.CartStatus status);
}