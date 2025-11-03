package com.example.bookstore.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.bookstore.models.Role;
import com.example.bookstore.services.RoleService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@AllArgsConstructor
@RequestMapping("/roles")
public class RoleController {
  @Autowired
  private final RoleService roleService;

  @GetMapping("")
  public String allRoles(Model model) {
    List<Role> roles = roleService.all();
    model.addAttribute("roles", roles);
    return "role/all";
  }
  
  @GetMapping("add")
  public String rolePage() {
    return "role/add";
  }
  
  @PostMapping("add")
  public String addRole(@RequestParam String name) {
    roleService.add(name);
    return "redirect:/roles";
  }
  
    @GetMapping("edit/{id}")
    public String editRole(@PathVariable Integer id, Model model) {
      Role role = roleService.getById(id);
      model.addAttribute("role", role);
      return "role/edit";
    }
  
    @PostMapping("edit/{id}")
    public String updateRole(@PathVariable Integer id, @RequestParam String name) {
      roleService.edit(id, name);
      return "redirect:/roles";
    }
    
    @GetMapping("drop/{id}")
    public String dropRole(@PathVariable Integer id) {
      roleService.drop(id);
      return "redirect:/roles";
    }
    
  
  
}
