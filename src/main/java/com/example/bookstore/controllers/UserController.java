package com.example.bookstore.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.bookstore.dtos.UserDto;
import com.example.bookstore.models.AppUser;
import com.example.bookstore.models.Role;
import com.example.bookstore.services.RoleService;
import com.example.bookstore.services.UserService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
@AllArgsConstructor
public class UserController {

  @Autowired
  private final UserService userService;

  @Autowired
  private final RoleService roleService;

  @Autowired
  private final PasswordEncoder passwordEncoder;

  @GetMapping("login")
  public String loginPage() {
    return "user/login";
  }

  @GetMapping("register")
  public String registerPage() {
    return "user/register";
  }
  
  @PostMapping("register")
  public String register(@ModelAttribute UserDto userDto) {
    AppUser user = new AppUser();
    user.setUsername(userDto.getUsername());
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    user.setEmail(userDto.getEmail());
    userService.addUser(user);
    return "redirect:/login";
  }
  
    @GetMapping("users")
    public String all(Model model) {
      List<AppUser> users = userService.getAll();
      model.addAttribute("users", users);
      return "user/all";
    }
  
    @GetMapping("users/edit{id}")
    public String getMethodName(@PathVariable Long id, Model model) {
      List<Role> roles = roleService.all();
      AppUser user = userService.getUserById(id);
      Set<Role> userRoles = user.getRoles();

      model.addAttribute("roles", roles);
      model.addAttribute("userRoles", userRoles);
      model.addAttribute("userId", id);
      return "users/edit";
    }

    @GetMapping("users/add/role/{uid}/{rid}")
    public String addRoleToUser(@PathVariable Long uid, @PathVariable Integer rid, Model model) {
      userService.addRoleToUser(uid, rid);
      return "redirect:/users";
    }
    
    @GetMapping("users/remove/role/{uid}/{rid}")
    public String removeRoleFromUser(@PathVariable Long uid, @PathVariable Integer rid, Model model) {
      userService.removeRoleFromUser(uid, rid);
        return "redirect:/users";
    }
    
    
    
  
  
}
