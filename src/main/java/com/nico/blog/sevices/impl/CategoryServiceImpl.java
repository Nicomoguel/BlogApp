package com.nico.blog.sevices.impl;

import com.nico.blog.domain.entities.Category;
import com.nico.blog.repositories.CategoryRepository;
import com.nico.blog.sevices.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository; // out repo database

    @Override
    public List<Category> listCategories() {
        return categoryRepository.findAllWithPostCount(); // finds all the categories on the database
    }

    @Override
    @Transactional // we are making multiple database calls, and we want them all in the same transaction
    public Category createCategory(Category category) {
        String name = category.getName();
        if(categoryRepository.existsByNameIgnoreCase(name)){
            throw new IllegalArgumentException("Category already exists with name " + name);
        }

        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(UUID id) {

        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            if(!category.get().getPosts().isEmpty()){
                throw new IllegalStateException("Category has posts associated with it");
            }
            categoryRepository.deleteById(id);
        }


    }
}
