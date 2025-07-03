package edu.sena.creamuebles.controller;

import edu.sena.creamuebles.dto.CartItemRequestDTO;
import edu.sena.creamuebles.dto.CartResponseDTO;
import edu.sena.creamuebles.model.User;
import edu.sena.creamuebles.service.CartService;
import edu.sena.creamuebles.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService; // Para buscar el User a partir del Principal

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<CartResponseDTO> getCart(Principal principal, HttpServletRequest request) {
        Optional<User> user = getUserFromPrincipal(principal);
        String sessionId = request.getSession().getId();
        return ResponseEntity.ok(cartService.getCart(user, sessionId));
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponseDTO> addItemToCart(@Valid @RequestBody CartItemRequestDTO itemDTO,
                                                         Principal principal, HttpServletRequest request) {
        Optional<User> user = getUserFromPrincipal(principal);
        String sessionId = request.getSession().getId();
        return ResponseEntity.ok(cartService.addItemToCart(user, sessionId, itemDTO));
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<CartResponseDTO> removeItemFromCart(@PathVariable Long productId,
                                                              Principal principal, HttpServletRequest request) {
        Optional<User> user = getUserFromPrincipal(principal);
        String sessionId = request.getSession().getId();
        return ResponseEntity.ok(cartService.removeItemFromCart(user, sessionId, productId));
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(Principal principal, HttpServletRequest request) {
        Optional<User> user = getUserFromPrincipal(principal);
        String sessionId = request.getSession().getId();
        cartService.clearCart(user, sessionId);
        return ResponseEntity.noContent().build();
    }

    private Optional<User> getUserFromPrincipal(Principal principal) {
        if (principal != null) {
            // Asumimos que el 'name' del Principal es el email o username
            return userService.findByEmail(principal.getName());
        }
        return Optional.empty();
    }
}