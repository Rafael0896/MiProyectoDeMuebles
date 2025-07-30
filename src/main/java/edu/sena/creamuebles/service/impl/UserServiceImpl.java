package edu.sena.creamuebles.service.impl;

import edu.sena.creamuebles.dto.UserRegistrationDTO;
import edu.sena.creamuebles.dto.UserResponseDTO;
import edu.sena.creamuebles.dto.UserUpdateDTO;
import edu.sena.creamuebles.model.User;
import edu.sena.creamuebles.repository.UserRepository;
import edu.sena.creamuebles.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponseDTO registerNewUser(UserRegistrationDTO registrationDTO) {
        // Esta validación para evitar emails duplicados es excelente.
        userRepository.findByEmailIgnoreCase(registrationDTO.email()).ifPresent(u -> {
            throw new IllegalStateException("El email ya está en uso: " + registrationDTO.email());
        });

        // --- CORRECCIÓN Y MEJORA: Usar el patrón Builder y añadir los campos faltantes ---
        User newUser = User.builder()
                .firstName(registrationDTO.firstName())
                .lastName(registrationDTO.lastName())
                .email(registrationDTO.email())
                .documentType(registrationDTO.documentType())       // <-- CAMPO AÑADIDO
                .documentNumber(registrationDTO.documentNumber())   // <-- CAMPO AÑADIDO
                .phone(registrationDTO.phone())                     // <-- CAMPO AÑADIDO
                .password(passwordEncoder.encode(registrationDTO.password()))
                .role(User.Role.USER)
                .enabled(true) // Es buena práctica ser explícito con el estado inicial
                .build();

        User savedUser = userRepository.save(newUser);
        return mapToResponseDTO(savedUser);
    }

    @Override
    @Transactional
    public Optional<UserResponseDTO> updateUser(Long id, UserUpdateDTO updateDTO) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    // La validación para el email en la actualización está muy bien pensada.
                    if (updateDTO.email() != null && !existingUser.getEmail().equalsIgnoreCase(updateDTO.email())) {
                        userRepository.findByEmailIgnoreCase(updateDTO.email()).ifPresent(otherUser -> {
                            throw new IllegalStateException("El email '" + updateDTO.email() + "' ya está en uso por otro usuario.");
                        });
                        existingUser.setEmail(updateDTO.email());
                    }

                    // Actualizar solo los campos que vienen en el DTO de actualización
                    if (updateDTO.firstName() != null) existingUser.setFirstName(updateDTO.firstName());
                    if (updateDTO.lastName() != null) existingUser.setLastName(updateDTO.lastName());
                    if (updateDTO.phone() != null) existingUser.setPhone(updateDTO.phone());
                    if (updateDTO.address() != null) existingUser.setAddress(updateDTO.address());

                    User updatedUser = userRepository.save(existingUser);
                    return mapToResponseDTO(updatedUser);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("No se encontró un usuario con el email: " + email));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponseDTO> findById(Long id) {
        return userRepository.findById(id).map(this::mapToResponseDTO);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Este método de mapeo es una excelente práctica para no repetir código.
    private UserResponseDTO mapToResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getRole()
        );
    }
}