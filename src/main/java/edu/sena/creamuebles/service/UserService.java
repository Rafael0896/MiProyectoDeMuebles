package edu.sena.creamuebles.service;

import edu.sena.creamuebles.dto.UserRegistrationDTO;
import edu.sena.creamuebles.dto.UserResponseDTO;
import edu.sena.creamuebles.dto.UserUpdateDTO;
import edu.sena.creamuebles.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    UserResponseDTO registerNewUser(UserRegistrationDTO registrationDTO);

    Optional<User> findByEmail(String email);

    List<UserResponseDTO> findAll();

    Optional<UserResponseDTO> findById(Long id);

    Optional<UserResponseDTO> updateUser(Long id, UserUpdateDTO updateDTO);

    boolean deleteById(Long id);
}