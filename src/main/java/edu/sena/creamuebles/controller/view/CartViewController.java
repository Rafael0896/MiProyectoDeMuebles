package edu.sena.creamuebles.controller.view;

import edu.sena.creamuebles.dto.CartItemDTO;
import edu.sena.creamuebles.dto.ProductResponseDTO;
import edu.sena.creamuebles.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartViewController {

    private final ProductService productService;

    // Inyectamos el ProductService para poder buscar productos
    public CartViewController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Muestra la página del carrito de compras con los productos actuales.
     */
    @GetMapping("/view")
    @PreAuthorize("isAuthenticated()")
    public String showCartPage(HttpSession session, Model model) {
        // 1. Obtenemos el mapa de productos de la sesión
        Map<Long, Integer> cartMap = (Map<Long, Integer>) session.getAttribute("cart");

        // 2. Si el carrito existe y no está vacío, preparamos los datos para la vista
        if (cartMap != null && !cartMap.isEmpty()) {
            List<CartItemDTO> cartItems = new ArrayList<>();
            double total = 0.0;

            for (Map.Entry<Long, Integer> entry : cartMap.entrySet()) {
                Long productId = entry.getKey();
                Integer quantity = entry.getValue();

                // Buscamos el producto para obtener sus detalles (nombre, precio, imagen)
                ProductResponseDTO product = productService.findDtoById(productId).orElse(null);

                if (product != null) {
                    cartItems.add(new CartItemDTO(product, quantity));
                    total += product.effectivePrice()
                        .multiply(java.math.BigDecimal.valueOf(quantity))
                        .doubleValue();
                }
            }
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("cartTotal", total);
        }

        return "cart/view";
    }

    /**
     * Procesa la petición para añadir un producto al carrito.
     */
    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String addToCart(@RequestParam("productId") Long productId,
                            @RequestParam("quantity") int quantity,
                            HttpServletRequest request) {

        // 1. Obtenemos la sesión actual del usuario. Si no existe, se crea una.
        HttpSession session = request.getSession(true);

        // 2. Obtenemos el carrito de la sesión. Puede ser nulo la primera vez.
        //    El carrito será un Mapa donde la clave es el ID del producto y el valor es la cantidad.
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");

        // 3. Si no hay carrito en la sesión, creamos uno nuevo.
        if (cart == null) {
            cart = new HashMap<>();
        }

        // 4. Añadimos el producto al carrito.
        //    Si el producto ya estaba, suma la nueva cantidad a la existente.
        //    Si no estaba, lo añade con la cantidad especificada.
        cart.merge(productId, quantity, Integer::sum);

        // 5. ¡MUY IMPORTANTE! Guardamos el carrito actualizado de nuevo en la sesión.
        session.setAttribute("cart", cart);

        System.out.println("Carrito actualizado: " + cart);

        // 6. Redirigimos a la página de visualización del carrito.
        return "redirect:/cart/view";

    }
}