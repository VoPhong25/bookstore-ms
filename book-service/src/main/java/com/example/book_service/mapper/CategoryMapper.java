package com.example.book_service.mapper;

import com.example.book_service.dto.request.CategoryCreateRequest;
import com.example.book_service.dto.response.CategoryCreateResponse;
import com.example.book_service.emtity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryCreateRequest request);
    CategoryCreateResponse toCategoryCreateResponse(Category category);

    void updateCategory(@MappingTarget Category category, CategoryCreateRequest request);
}
