package edu.sena.creamuebles.repository;

import edu.sena.creamuebles.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca un usuario por su dirección de email, ignorando mayúsculas/minúsculas.
     * Es fundamental para el login y para evitar registros duplicados.
     */
    Optional<User> findByEmailIgnoreCase(String email);
}