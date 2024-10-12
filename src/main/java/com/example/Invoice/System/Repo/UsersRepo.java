package com.example.Invoice.System.Repo;

import com.example.Invoice.System.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<Users, String> {
    Optional<Users> findByEmail(String email);

    // Method to find unverified users older than 24 hours
    List<Users> findByVerifiedFalseAndCreatedAtBefore(LocalDateTime dateTime);
}