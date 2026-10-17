package com.nico.blog.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data // used by lombok to remove boilerplate code, we get getter, setter, toString, equals, hashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    // info that we need to show to the client
    private UUID id;
    private String name;
    private Long postCount;

}
