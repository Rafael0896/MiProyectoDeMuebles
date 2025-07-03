package edu.sena.creamuebles.controller.view;

import edu.sena.creamuebles.service.BannerService;
import edu.sena.creamuebles.service.CategoryService;
import edu.sena.creamuebles.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeViewController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final BannerService bannerService;

    // Inyectamos todos los servicios que necesitamos en el constructor
    public HomeViewController(ProductService productService, CategoryService categoryService, BannerService bannerService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.bannerService = bannerService;
    }

    @GetMapping({"/", "/home"})
    public String showHomePage(Model model) {
        // 1. Obtenemos los banners activos para el carrusel de promociones
        model.addAttribute("banners", bannerService.findAllActive());

        // 2. Obtenemos todas las categorías para que el usuario pueda explorar
        model.addAttribute("categories", categoryService.findAll());

        // 3. Obtenemos una selección de productos destacados (ej. los 6 más recientes)
        // Creamos una solicitud de página para obtener solo la primera página con 6 productos.
        Pageable featuredProductsPageable = PageRequest.of(0, 6, Sort.by("id").descending());
        model.addAttribute("products", productService.findAllActive(featuredProductsPageable));

        // Le decimos a Thymeleaf que renderice el archivo 'index.html' con todos estos datos
        return "index";
    }
}