package edu.sena.creamuebles.service.impl;

import edu.sena.creamuebles.dto.ProductRequestDTO;
import edu.sena.creamuebles.dto.ProductResponseDTO;
import edu.sena.creamuebles.model.Category;
import edu.sena.creamuebles.model.Product;
import edu.sena.creamuebles.repository.CategoryRepository;
import edu.sena.creamuebles.repository.ProductRepository;
import edu.sena.creamuebles.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    // Inyección de dependencias por constructor (mejor práctica)
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> findAllActive(Pageable pageable) {
        return productRepository.findByActiveTrue(pageable).map(this::convertToResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductResponseDTO> findById(Long id) {
        return productRepository.findById(id).map(this::convertToResponseDTO);
    }

    @Override
    @Transactional
    public ProductResponseDTO create(ProductRequestDTO requestDTO) {
        Category category = categoryRepository.findById(requestDTO.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + requestDTO.categoryId()));

        Product product = new Product();
        updateEntityFromDTO(product, requestDTO, category);

        Product savedProduct = productRepository.save(product);
        return convertToResponseDTO(savedProduct);
    }

    @Override
    @Transactional
    public Optional<ProductResponseDTO> update(Long id, ProductRequestDTO requestDTO) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    Category category = categoryRepository.findById(requestDTO.categoryId())
                            .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + requestDTO.categoryId()));

                    updateEntityFromDTO(existingProduct, requestDTO, category);
                    Product updatedProduct = productRepository.save(existingProduct);
                    return convertToResponseDTO(updatedProduct);
                });
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> findByCategoryId(Long categoryId, Pageable pageable) {
        return productRepository.findByCategoryIdAndActiveTrue(categoryId, pageable)
                .map(this::convertToResponseDTO);
    }

    @Override
    public Optional<ProductResponseDTO> findDtoById(Long id) {
        return productRepository.findById(id)
            .map(this::convertToResponseDTO);
    }

    // --- Métodos de Mapeo ---

    private ProductResponseDTO convertToResponseDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getSalePrice(),
                product.getStock(),
                product.getSku(),
                product.getImageUrl(),
                product.getAdditionalImages(),
                product.getWeight(),
                product.getDimensions(),
                product.getMaterial(),
                product.getColor(),
                product.isFeatured(),
                product.isActive(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                product.getCategory() != null ? product.getCategory().getName() : null,
                product.getEffectivePrice(),
                product.isOnSale(),
                product.isInStock(),
                product.getDiscountPercentage()
        );
    }

    private void updateEntityFromDTO(Product product, ProductRequestDTO dto, Category category) {
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setSalePrice(dto.salePrice());
        product.setStock(dto.stock());
        product.setSku(dto.sku());
        product.setImageUrl(dto.imageUrl());
        product.setAdditionalImages(dto.additionalImages());
        product.setWeight(dto.weight());
        product.setDimensions(dto.dimensions());
        product.setMaterial(dto.material());
        product.setColor(dto.color());
        product.setFeatured(dto.featured());
        product.setActive(dto.active());
        product.setCategory(category);
    }
}