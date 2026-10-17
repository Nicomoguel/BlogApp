package com.nico.blog.mappers;

// Declare this as a MapStruct mapper

import com.nico.blog.domain.PostStatus;
import com.nico.blog.domain.dtos.CategoryDto;
import com.nico.blog.domain.dtos.CreateCategoryRequest;
import com.nico.blog.domain.entities.Category;
import com.nico.blog.domain.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/* means when it can't map this properties to the target
   we are just going to ignore them and not throw an exception
 */
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    // we need to populate the value of post count in our dto
    @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
    CategoryDto toDto(Category category);

    Category toEntity(CreateCategoryRequest createCategoryRequest);


    // mapstruct label for a conversion method, connects with the @Mapping of line 23
    // when toDto is called, it turns category.getPosts() into DTO's postCount
    @Named("calculatePostCount")
    default long calculatePostCount(List<Post> posts){
        if(null == posts){
            return 0;
        }
        // filter post to only get posts that are published and get the count
        return posts.stream().filter(post-> PostStatus.PUBLISHED.equals(post.getStatus())).count();

    }

    //
    //Category fromDto(CategoryDto categoryDto);



}
