package com.example.librairie_online.repository;

import com.example.librairie_online.entity.Role;
import com.example.librairie_online.enumeration.TypeRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    public Role findByRole(TypeRole role);
}
