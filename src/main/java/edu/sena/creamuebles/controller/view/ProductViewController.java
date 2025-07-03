package edu.sena.creamuebles.controller.view;

import edu.sena.creamuebles.dto.ProductResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import edu.sena.creamuebles.service.ProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products") // Todas las rutas de este controlador empiezan con /products
public class ProductViewController {

    private final ProductService productService;

    public ProductViewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String showProductListPage(Model model, @PageableDefault(size = 9, sort = "name") Pageable pageable) {
        // Llama al servicio para obtener los productos activos de forma paginada
        model.addAttribute("page", productService.findAllActive(pageable));
        // Devuelve el nombre de la plantilla que se debe renderizar
        return "products/list";
    }

    // Aquí añadiremos el método para ver el detalle de un producto más adelante
    // @GetMapping("/{id}")
    // public String showProductDetailPage(...) { ... }

    // ... (tus otras importaciones y la clase)

    /**
     * Muestra la página de detalles de un producto específico.
     * Escucha en URLs como /products/1, /products/2, etc.
     *
     * @param id El ID del producto que viene en la URL.
     * @param model El objeto para pasar datos a la vista.
     * @return El nombre del archivo HTML que se debe mostrar.
     */
    @GetMapping("/{id}")
    public String showProductDetails(@PathVariable("id") Long id, Model model) {
        // 1. Buscamos el producto por su ID usando el servicio.
        //    Usamos orElseThrow para que, si el producto no existe, se muestre un error 404 (Not Found).
        ProductResponseDTO productDto = productService.findDtoById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        // 2. Añadimos el DTO del producto encontrado al modelo.
        //    La vista podrá acceder a él con el nombre "product".
        model.addAttribute("product", productDto);

        // 3. Le decimos a Spring que renderice la plantilla 'products/detail.html'.
        return "products/detail";
    }

// ... (resto de la clase)
}