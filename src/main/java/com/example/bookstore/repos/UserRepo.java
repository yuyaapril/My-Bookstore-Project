package com.example.bookstore.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookstore.models.AppUser;
@Repository

public interface UserRepo extends JpaRepository<AppUser,Long> {  
  Optional<AppUser> findByUsername(String name);
}
