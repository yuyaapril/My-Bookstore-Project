package com.example.bookstore.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor

public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  //Getter
  //public Integer getId() {
  //  return id;
  //}
  //public String getName() {
  //  return name;
  //}

  //Setter
  //public void setName(String name) {
  //  this.name = name;
  //}
  //

  //Constructor
  //public Role() {
  //  
  //}

}
