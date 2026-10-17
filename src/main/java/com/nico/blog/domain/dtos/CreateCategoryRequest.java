package com.nico.blog.domain.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    We are creating a dedicated class for the body of the category we are trying to create,
    only needs id and name
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryRequest {

    @NotBlank(message = "Category name is requires") // name cannot be null and it cannot be an empty string
    @Size(min = 2, max = 50, message = "Category name must be between {min} and {max} characters") // to ensure the length is between 2 and 50
    @Pattern(regexp = "^[\\w\\s-]+$", message = "Category name con only contain letters, numbers, spaces and hyphens") // regex to meet the requierements of the name
    private String name;




}
