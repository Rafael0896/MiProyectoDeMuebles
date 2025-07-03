package edu.sena.creamuebles.service;

import edu.sena.creamuebles.dto.CategoryRequestDTO;
import edu.sena.creamuebles.dto.CategoryResponseDTO;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<CategoryResponseDTO> findAll();

    List<CategoryResponseDTO> findAllActive();

    Optional<CategoryResponseDTO> findById(Long id);

    CategoryResponseDTO create(CategoryRequestDTO categoryRequestDTO);

    Optional<CategoryResponseDTO> update(Long id, CategoryRequestDTO categoryRequestDTO);

    boolean deleteById(Long id);
}