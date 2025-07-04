package com.futureim.voicecommerce.auth.domain.service;

import com.futureim.voicecommerce.auth.domain.model.Permission;
import com.futureim.voicecommerce.auth.domain.model.Role;
import com.futureim.voicecommerce.auth.domain.model.User;
import com.futureim.voicecommerce.auth.domain.repository.RoleRepository;
import com.futureim.voicecommerce.auth.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Assign default role if no roles are assigned
        if (user.getRoles().isEmpty()) {
            Role defaultRole = roleRepository.findByIsDefaultTrue()
                .orElseThrow(() -> new EntityNotFoundException("Default role not found"));
            user.addRole(defaultRole);
        }

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUserById(String id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User updateUser(String id, User updatedUser) {
        User existingUser = getUserById(id);

        // Update basic information
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setEnabled(updatedUser.isEnabled());

        // Update password if provided
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    @Transactional
    public void deleteUser(String id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    @Transactional
    public User assignRoles(String userId, Set<Role> roles) {
        User user = getUserById(userId);
        user.getRoles().clear();
        user.getRoles().addAll(roles);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public boolean hasPermission(String userId, Permission permission) {
        User user = getUserById(userId);
        return user.hasPermission(permission);
    }

    @Transactional(readOnly = true)
    public boolean hasRole(String userId, String roleName) {
        User user = getUserById(userId);
        return user.hasRole(roleName);
    }

    @Transactional
    public void lockUser(String userId) {
        User user = getUserById(userId);
        user.setAccountNonLocked(false);
        userRepository.save(user);
    }

    @Transactional
    public void unlockUser(String userId) {
        User user = getUserById(userId);
        user.setAccountNonLocked(true);
        userRepository.save(user);
    }
}
