package edu.sena.creamuebles.service;

import edu.sena.creamuebles.dto.BannerRequestDTO;
import edu.sena.creamuebles.dto.BannerResponseDTO;
import edu.sena.creamuebles.model.Banner;

import java.util.List;
import java.util.Optional;

public interface BannerService {

    List<BannerResponseDTO> findAllActive();

    List<BannerResponseDTO> findAll();

    List<BannerResponseDTO> findCurrentlyActive();

    List<BannerResponseDTO> findCurrentlyActiveByType(Banner.BannerType type);

    Optional<BannerResponseDTO> findById(Long id);

    BannerResponseDTO create(BannerRequestDTO bannerRequestDTO);

    Optional<BannerResponseDTO> update(Long id, BannerRequestDTO bannerRequestDTO);

    boolean deleteById(Long id);
}