package com.nico.blog.controllers;

import com.nico.blog.domain.dtos.CategoryDto;
import com.nico.blog.domain.dtos.CreateCategoryRequest;
import com.nico.blog.domain.entities.Category;
import com.nico.blog.mappers.CategoryMapper;
import com.nico.blog.sevices.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> listCategories(){
        List<CategoryDto> categories = categoryService.listCategories().stream().map(categoryMapper::toDto).toList(); // cast each category into categoryDto and then turn that into a list
        return ResponseEntity.ok(categories); // return http 200 with our categories
    }

    @PostMapping // @Valid means that only if the object meets the annotation criteria then the method will run
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest){
        Category createdCategory = categoryMapper.toEntity(createCategoryRequest);
        Category savedCategory = categoryService.createCategory(createdCategory);
        // first, we send the DTO and then the http response
        return new ResponseEntity<>(categoryMapper.toDto(savedCategory), HttpStatus.CREATED);
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
