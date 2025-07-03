package edu.sena.creamuebles.repository;

import edu.sena.creamuebles.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    // Encontrar productos activos con paginación
    Page<Product> findByActiveTrue(Pageable pageable);

    // Encontrar productos destacados y activos
    List<Product> findByFeaturedTrueAndActiveTrue();

    // Encontrar productos activos por categoría con paginación
    Page<Product> findByCategoryIdAndActiveTrue(Long categoryId, Pageable pageable);

    // Buscar un producto por su SKU (debe ser único)
    Optional<Product> findBySku(String sku);
}