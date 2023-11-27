package com.movieapp.model.entity;

import com.movieapp.model.enums.UserRoleEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Table(name = "roles")
@Entity
public class RoleEntity extends BaseEntity{

  @Enumerated(EnumType.STRING)
  private UserRoleEnum role;


  public UserRoleEnum getRole() {
    return role;
  }

  public RoleEntity setRole(UserRoleEnum role) {
    this.role = role;
    return this;
  }
}