package edu.sena.creamuebles.service;

import edu.sena.creamuebles.dto.ProductRequestDTO;
import edu.sena.creamuebles.dto.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {

    Page<ProductResponseDTO> findAllActive(Pageable pageable);

    Optional<ProductResponseDTO> findById(Long id);

    ProductResponseDTO create(ProductRequestDTO productRequestDTO);

    Optional<ProductResponseDTO> update(Long id, ProductRequestDTO productRequestDTO);

    Optional<ProductResponseDTO> findDtoById(Long id);

    boolean deleteById(Long id);

    Page<ProductResponseDTO> findByCategoryId(Long categoryId, Pageable pageable);
}