package com.example.odziezowy.Repository;

import com.example.odziezowy.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
