package edu.sena.creamuebles.dto;

/**
 * Representa un item dentro del carrito de compras, combinando
 * los detalles del producto con la cantidad seleccionada.
 */
public record CartItemDTO(
        ProductResponseDTO product,
        int quantity
) {
    // Método de conveniencia para calcular el subtotal de este item
    public double getSubtotal() {
        return product.effectivePrice()
                .multiply(java.math.BigDecimal.valueOf(quantity))
                .doubleValue();
    }
}