package com.example.bookstore.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.bookstore.configs.MyUserDetail;
import com.example.bookstore.models.AppUser;
import com.example.bookstore.repos.UserRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService{
  @Autowired
  private final UserRepo userRepo;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<AppUser> user = userRepo.findByUsername(username);
    if (user.isPresent()) {
      return new MyUserDetail(user.get());
    } else {
      throw new UsernameNotFoundException("User Not Found");
    }

    
  }
  
}
