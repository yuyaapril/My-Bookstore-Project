package com.example.bookstore.dtos;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {

  @NotEmpty(message="Title can't be Empty")
  private String title;
  private String author;
  private String publicationDate;
  private String description;
  private MultipartFile coverImage; //coverImageUrl
  private Double price;
  private Integer stock;
  private Integer categoryId;

}
