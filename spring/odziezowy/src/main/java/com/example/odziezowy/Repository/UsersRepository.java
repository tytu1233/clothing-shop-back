package com.example.odziezowy.Repository;

import com.example.odziezowy.Model.Roles;
import com.example.odziezowy.Model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Page<Users> findAll(Pageable pageable);
    Page<Users> findAllByRoles(Pageable pageable, Optional<Roles> roles);
    Optional<Users> findByLoginAndPassword(String login, String password);
    Optional<Users> findByEmail(String email);
}
