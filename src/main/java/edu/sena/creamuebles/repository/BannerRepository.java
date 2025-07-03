package edu.sena.creamuebles.repository;

import edu.sena.creamuebles.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {

    /**
     * Encuentra todos los banners que están activos y dentro de su rango de fechas de validez.
     * Ordenados por el campo 'sortOrder'.
     */
    List<Banner> findByActiveTrue();
    @Query("SELECT b FROM Banner b WHERE b.active = true AND " +
            "(b.startDate IS NULL OR b.startDate <= :now) AND " +
            "(b.endDate IS NULL OR b.endDate >= :now) " +
            "ORDER BY b.sortOrder ASC")
    List<Banner> findCurrentlyActiveBanners(@Param("now") LocalDateTime now);

    /**
     * Encuentra todos los banners de un tipo específico que están activos y
     * dentro de su rango de fechas de validez. Ordenados por 'sortOrder'.
     */
    @Query("SELECT b FROM Banner b WHERE b.active = true AND b.type = :type AND " +
            "(b.startDate IS NULL OR b.startDate <= :now) AND " +
            "(b.endDate IS NULL OR b.endDate >= :now) " +
            "ORDER BY b.sortOrder ASC")
    List<Banner> findCurrentlyActiveBannersByType(@Param("type") Banner.BannerType type,
                                                  @Param("now") LocalDateTime now);
}