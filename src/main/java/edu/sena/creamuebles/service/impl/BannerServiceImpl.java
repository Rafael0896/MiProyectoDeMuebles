package edu.sena.creamuebles.service.impl;

import edu.sena.creamuebles.dto.BannerRequestDTO;
import edu.sena.creamuebles.dto.BannerResponseDTO;
import edu.sena.creamuebles.model.Banner;
import edu.sena.creamuebles.repository.BannerRepository;
import edu.sena.creamuebles.service.BannerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;

    public BannerServiceImpl(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BannerResponseDTO> findAllActive() { // <-- CAMBIO 1: Devuelve el DTO de respuesta
        List<Banner> activeBanners = bannerRepository.findByActiveTrue();

        // Reutilizamos el método de mapeo existente, que es más completo y consistente
        return activeBanners.stream()
                .map(this::mapToResponseDTO) // <-- CAMBIO 2: Reutilización de código
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BannerResponseDTO> findAll() {
        return bannerRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BannerResponseDTO> findCurrentlyActive() {
        return bannerRepository.findCurrentlyActiveBanners(LocalDateTime.now()).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BannerResponseDTO> findCurrentlyActiveByType(Banner.BannerType type) {
        return bannerRepository.findCurrentlyActiveBannersByType(type, LocalDateTime.now()).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BannerResponseDTO> findById(Long id) {
        return bannerRepository.findById(id).map(this::mapToResponseDTO);
    }

    @Override
    @Transactional
    public BannerResponseDTO create(BannerRequestDTO requestDTO) {
        Banner banner = new Banner();
        mapToEntity(banner, requestDTO);
        Banner savedBanner = bannerRepository.save(banner);
        return mapToResponseDTO(savedBanner);
    }

    @Override
    @Transactional
    public Optional<BannerResponseDTO> update(Long id, BannerRequestDTO requestDTO) {
        return bannerRepository.findById(id)
                .map(existingBanner -> {
                    mapToEntity(existingBanner, requestDTO);
                    Banner updatedBanner = bannerRepository.save(existingBanner);
                    return mapToResponseDTO(updatedBanner);
                });
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if (bannerRepository.existsById(id)) {
            bannerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // --- Métodos de Mapeo ---

    private BannerResponseDTO mapToResponseDTO(Banner banner) {
        return new BannerResponseDTO(
                banner.getId(),
                banner.getTitle(),
                banner.getSubtitle(),
                banner.getDescription(),
                banner.getImageUrl(),
                banner.getLinkUrl(),
                banner.getLinkText(),
                banner.isActive(),
                banner.getSortOrder(),
                banner.getType(),
                banner.getStartDate(),
                banner.getEndDate(),
                banner.isCurrentlyActive() // Usamos el método auxiliar del modelo
        );
    }

    private void mapToEntity(Banner banner, BannerRequestDTO dto) {
        banner.setTitle(dto.title());
        banner.setSubtitle(dto.subtitle());
        banner.setDescription(dto.description());
        banner.setImageUrl(dto.imageUrl());
        banner.setLinkUrl(dto.linkUrl());
        banner.setLinkText(dto.linkText());
        banner.setActive(dto.active());
        banner.setSortOrder(dto.sortOrder());
        banner.setType(dto.type());
        banner.setStartDate(dto.startDate());
        banner.setEndDate(dto.endDate());
    }
}