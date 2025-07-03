package edu.sena.creamuebles.controller;

import edu.sena.creamuebles.dto.BannerRequestDTO;
import edu.sena.creamuebles.dto.BannerResponseDTO;
import edu.sena.creamuebles.model.Banner;
import edu.sena.creamuebles.service.BannerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/banners")
public class BannerController {

    private final BannerService bannerService;

    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    // Endpoint público para obtener banners activos (ej. para la página principal)
    @GetMapping("/active")
    public ResponseEntity<List<BannerResponseDTO>> getActiveBanners(
            @RequestParam(required = false) Banner.BannerType type) {
        List<BannerResponseDTO> banners;
        if (type != null) {
            banners = bannerService.findCurrentlyActiveByType(type);
        } else {
            banners = bannerService.findCurrentlyActive();
        }
        return ResponseEntity.ok(banners);
    }

    // --- Endpoints de Administración ---

    // Endpoint para obtener todos los banners (para un panel de admin)
    @GetMapping
    public ResponseEntity<List<BannerResponseDTO>> getAllBanners() {
        return ResponseEntity.ok(bannerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BannerResponseDTO> getBannerById(@PathVariable Long id) {
        return bannerService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BannerResponseDTO> createBanner(@Valid @RequestBody BannerRequestDTO bannerRequestDTO) {
        BannerResponseDTO createdBanner = bannerService.create(bannerRequestDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdBanner.id())
                .toUri();
        return ResponseEntity.created(location).body(createdBanner);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BannerResponseDTO> updateBanner(@PathVariable Long id, @Valid @RequestBody BannerRequestDTO bannerRequestDTO) {
        return bannerService.update(id, bannerRequestDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Long id) {
        if (bannerService.deleteById(id)) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}