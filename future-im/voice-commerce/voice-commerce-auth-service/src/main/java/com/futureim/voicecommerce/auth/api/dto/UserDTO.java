package com.futureim.voicecommerce.auth.api.dto;

import com.futureim.voicecommerce.auth.domain.model.Permission;
import com.futureim.voicecommerce.auth.domain.model.Role;
import com.futureim.voicecommerce.auth.domain.model.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class UserDTO {
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private boolean enabled;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;
    private Set<String> roles;
    private Set<String> permissions;

    public static UserDTO fromUser(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setEnabled(user.isEnabled());
        dto.setLastLoginAt(user.getLastLoginAt());
        dto.setCreatedAt(user.getCreatedAt());
        
        // Map roles to role names
        dto.setRoles(user.getRoles().stream()
            .map(Role::getName)
            .collect(Collectors.toSet()));
        
        // Map permissions to permission names
        dto.setPermissions(user.getRoles().stream()
            .flatMap(role -> role.getPermissions().stream())
            .map(Permission::name)
            .collect(Collectors.toSet()));
        
        return dto;
    }
}
