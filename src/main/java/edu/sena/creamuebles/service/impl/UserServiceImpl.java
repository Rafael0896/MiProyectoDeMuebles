package edu.sena.creamuebles.service.impl;

import edu.sena.creamuebles.dto.UserRegistrationDTO;
import edu.sena.creamuebles.dto.UserResponseDTO;
import edu.sena.creamuebles.dto.UserUpdateDTO;
import edu.sena.creamuebles.model.User;
import edu.sena.creamuebles.repository.UserRepository;
import edu.sena.creamuebles.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserResponseDTO registerNewUser(UserRegistrationDTO registrationDTO) {
        userRepository.findByEmailIgnoreCase(registrationDTO.email()).ifPresent(u -> {
            throw new IllegalStateException("El email ya está en uso: " + registrationDTO.email());
        });

        User newUser = new User();
        newUser.setFirstName(registrationDTO.firstName());
        newUser.setLastName(registrationDTO.lastName());
        newUser.setEmail(registrationDTO.email());
        // ¡Importante! Codificar la contraseña antes de guardarla
        newUser.setPassword(passwordEncoder.encode(registrationDTO.password()));
        newUser.setRole(User.Role.USER);
        newUser.setEnabled(true);

        User savedUser = userRepository.save(newUser);
        return mapToResponseDTO(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
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
    public Optional<UserResponseDTO> updateUser(Long id, UserUpdateDTO updateDTO) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setFirstName(updateDTO.firstName());
                    existingUser.setLastName(updateDTO.lastName());
                    existingUser.setEmail(updateDTO.email());
                    existingUser.setPhone(updateDTO.phone());
                    existingUser.setAddress(updateDTO.address());
                    existingUser.setRole(updateDTO.role());
                    existingUser.setEnabled(updateDTO.enabled());
                    User updatedUser = userRepository.save(existingUser);
                    return mapToResponseDTO(updatedUser);
                });
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

    // --- Implementación de UserDetailsService ---
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("No se encontró un usuario con el email: " + email));
    }

    // --- Método de Mapeo ---
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