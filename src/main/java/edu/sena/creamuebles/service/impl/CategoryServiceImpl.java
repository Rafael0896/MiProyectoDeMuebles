package edu.sena.creamuebles.service.impl;

import edu.sena.creamuebles.dto.CategoryRequestDTO;
import edu.sena.creamuebles.dto.CategoryResponseDTO;
import edu.sena.creamuebles.model.Category;
import edu.sena.creamuebles.repository.CategoryRepository;
import edu.sena.creamuebles.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> findAllActive() {
        return categoryRepository.findByActiveTrueOrderBySortOrderAsc().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryResponseDTO> findById(Long id) {
        return categoryRepository.findById(id).map(this::mapToResponseDTO);
    }

    @Override
    @Transactional
    public CategoryResponseDTO create(CategoryRequestDTO requestDTO) {
        // Validación de negocio: no permitir nombres duplicados
        categoryRepository.findByNameIgnoreCase(requestDTO.name()).ifPresent(c -> {
            throw new IllegalStateException("Ya existe una categoría con el nombre: " + requestDTO.name());
        });

        Category category = new Category();
        mapToEntity(category, requestDTO);
        Category savedCategory = categoryRepository.save(category);
        return mapToResponseDTO(savedCategory);
    }

    @Override
    @Transactional
    public Optional<CategoryResponseDTO> update(Long id, CategoryRequestDTO requestDTO) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    // Opcional: validar si el nuevo nombre ya lo usa OTRA categoría
                    categoryRepository.findByNameIgnoreCase(requestDTO.name()).ifPresent(c -> {
                        if (!c.getId().equals(existingCategory.getId())) {
                            throw new IllegalStateException("El nombre '" + requestDTO.name() + "' ya está en uso por otra categoría.");
                        }
                    });

                    mapToEntity(existingCategory, requestDTO);
                    Category updatedCategory = categoryRepository.save(existingCategory);
                    return mapToResponseDTO(updatedCategory);
                });
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if (categoryRepository.existsById(id)) {
            // Consideración: ¿Qué pasa con los productos de esta categoría?
            // La configuración CascadeType.ALL los borraría. Si no es el comportamiento deseado,
            // se necesitaría lógica adicional aquí (ej. reasignar productos o impedir el borrado).
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // --- Métodos de Mapeo ---

    private CategoryResponseDTO mapToResponseDTO(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getImageUrl(),
                category.isActive(),
                category.getSortOrder(),
                category.getProductCount() // Usamos el método auxiliar del modelo
        );
    }

    private void mapToEntity(Category category, CategoryRequestDTO dto) {
        category.setName(dto.name());
        category.setDescription(dto.description());
        category.setImageUrl(dto.imageUrl());
        category.setActive(dto.active());
        category.setSortOrder(dto.sortOrder());
    }
}