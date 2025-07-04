package com.futureim.voicecommerce.auth.domain.service;

import com.futureim.voicecommerce.auth.domain.model.Permission;
import com.futureim.voicecommerce.auth.domain.model.Role;
import com.futureim.voicecommerce.auth.domain.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    @Transactional
    public Role createRole(Role role) {
        if (roleRepository.existsByName(role.getName())) {
            throw new IllegalArgumentException("Role already exists with name: " + role.getName());
        }
        return roleRepository.save(role);
    }

    @Transactional(readOnly = true)
    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name)
            .orElseThrow(() -> new EntityNotFoundException("Role not found with name: " + name));
    }

    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Transactional
    public Role updateRole(Long id, Role updatedRole) {
        Role existingRole = getRoleById(id);
        
        // Update basic information
        existingRole.setName(updatedRole.getName());
        existingRole.setDescription(updatedRole.getDescription());
        existingRole.setDefault(updatedRole.isDefault());
        
        return roleRepository.save(existingRole);
    }

    @Transactional
    public void deleteRole(Long id) {
        Role role = getRoleById(id);
        if (role.isSystem()) {
            throw new IllegalStateException("Cannot delete system role");
        }
        roleRepository.delete(role);
    }

    @Transactional
    public Role assignPermissions(Long roleId, Set<Permission> permissions) {
        Role role = getRoleById(roleId);
        role.getPermissions().clear();
        role.getPermissions().addAll(permissions);
        return roleRepository.save(role);
    }

    @Transactional
    public Role addPermission(Long roleId, Permission permission) {
        Role role = getRoleById(roleId);
        role.addPermission(permission);
        return roleRepository.save(role);
    }

    @Transactional
    public Role removePermission(Long roleId, Permission permission) {
        Role role = getRoleById(roleId);
        role.removePermission(permission);
        return roleRepository.save(role);
    }

    @Transactional
    public void initializeDefaultRoles() {
        // Create ADMIN role if it doesn't exist
        if (!roleRepository.existsByName("ADMIN")) {
            Role adminRole = new Role("ADMIN");
            adminRole.setDescription("Administrator role with full system access");
            adminRole.setSystem(true);
            for (Permission permission : Permission.values()) {
                adminRole.addPermission(permission);
            }
            roleRepository.save(adminRole);
        }

        // Create USER role if it doesn't exist
        if (!roleRepository.existsByName("USER")) {
            Role userRole = new Role("USER");
            userRole.setDescription("Default user role with basic permissions");
            userRole.setDefault(true);
            userRole.setSystem(true);
            userRole.addPermission(Permission.PRODUCT_READ);
            userRole.addPermission(Permission.CART_CREATE);
            userRole.addPermission(Permission.CART_READ);
            userRole.addPermission(Permission.CART_UPDATE);
            userRole.addPermission(Permission.CART_DELETE);
            userRole.addPermission(Permission.ORDER_CREATE);
            userRole.addPermission(Permission.ORDER_READ);
            roleRepository.save(userRole);
        }
    }
}
