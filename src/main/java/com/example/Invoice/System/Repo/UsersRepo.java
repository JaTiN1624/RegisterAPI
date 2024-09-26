package com.example.Invoice.System.Repo;

import com.example.Invoice.System.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepo extends JpaRepository<Users, String> {
    Optional<Users> findByEmail(String email);

}
