package edu.sena.creamuebles.repository;

import edu.sena.creamuebles.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Encuentra todas las categorías activas, ordenadas por el campo 'sortOrder'.
     * Ideal para mostrar en menús de navegación.
     */
    List<Category> findByActiveTrueOrderBySortOrderAsc();

    /**
     * Busca una categoría por su nombre, ignorando mayúsculas y minúsculas.
     * Útil para validar que no se creen categorías con nombres duplicados.
     */
    Optional<Category> findByNameIgnoreCase(String name);
}