package com.movieapp.model.dto.role;

import com.movieapp.model.entity.RoleEntity;

import java.util.List;
import java.util.stream.Collectors;

public class RoleDto {
    private Long id;
    private String roleName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public static List<RoleDto> createFromRoles(List<RoleEntity> roles) {
        return roles.stream()
                .map(role -> {
                    RoleDto roleDto = new RoleDto();
                    roleDto.setId(role.getId());
                    roleDto.setRoleName(String.valueOf(role.getRole()));
                    return roleDto;
                })
                .collect(Collectors.toList());
    }
}
