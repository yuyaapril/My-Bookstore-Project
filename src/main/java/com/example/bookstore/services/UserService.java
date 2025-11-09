package com.example.bookstore.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.bookstore.configs.MyUserDetail;
import com.example.bookstore.models.AppUser;
import com.example.bookstore.models.Role;
import com.example.bookstore.repos.RoleRepo;
import com.example.bookstore.repos.UserRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService{
  @Autowired
  private final UserRepo userRepo;

  @Autowired
  private final RoleRepo roleRepo;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<AppUser> user = userRepo.findByUsername(username);
    if (user.isPresent()) {
      return new MyUserDetail(user.get());
    } else {
      throw new UsernameNotFoundException("User Not Found");
    }
  }

  public List<AppUser> getAll() {
    return userRepo.findAll();
  }

  public void addUser(AppUser user) {
    userRepo.save(user);
  }

  public AppUser getUserById(Long id) {
    Optional<AppUser> user = userRepo.findById(id);
    if (user.isPresent()) {
      return user.get();
    } else {
      throw new RuntimeException("No User with that id");
    }
  }

  public void addRoleToUser(Long userId, Integer roleId) {
    Optional<Role> role = roleRepo.findById(roleId);
    if (role.isPresent()) {
      AppUser user = getUserById(userId);
      Set<Role> roles = user.getRoles();
      roles.add(role.get());
      userRepo.save(user);
    } else {
      throw new RuntimeException("No Role with that id");
    }
  }

  public void removeRoleFromUser(Long userId, Integer roleId) {
    Optional<Role> role = roleRepo.findById(roleId);
    if (role.isPresent()) {
      AppUser user = getUserById(userId);
      Set<Role> roles = user.getRoles();
      roles.remove(role.get());
      userRepo.save(user);
    } else {
      throw new RuntimeException("No Role with that id");
    }
  }
  
  public MyUserDetail getCurrentUserDetail() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    MyUserDetail userDetail = (MyUserDetail)auth.getPrincipal();
    return userDetail;
  }
  public AppUser getCurrentUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    MyUserDetail userDetail = (MyUserDetail)auth.getPrincipal();
    return userDetail.getUser();
  }

}
