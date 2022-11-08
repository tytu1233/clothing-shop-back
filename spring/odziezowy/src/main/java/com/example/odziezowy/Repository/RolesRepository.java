package com.example.odziezowy.Repository;

import com.example.odziezowy.Model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, Long> {
    Roles findByRoleName(String name);
}
