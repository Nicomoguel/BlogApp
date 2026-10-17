package com.nico.blog.repositories;

import com.nico.blog.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    // the n+1 problem means that the app runs n+1 queries for n categories
    // LEFT JOIN FETCH c.posts tells Hibernate to lead each category and its posts in the same query
    // LEFT keeps categories that have no posts
    // @Query used to specify a hql query to use
    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.posts")
    List<Category> findAllWithPostCount();

    boolean existsByNameIgnoreCase(String name); // I don't know how but spring data JPA will know exactly what to do with this



}
