package com.example.book_service.service;

import com.example.book_service.dto.request.CategoryCreateRequest;
import com.example.book_service.dto.response.CategoryCreateResponse;
import com.example.book_service.emtity.Category;
import com.example.book_service.mapper.CategoryMapper;
import com.example.book_service.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    public CategoryCreateResponse createCategory(CategoryCreateRequest request) {
        Category category = categoryMapper.toCategory(request);
        categoryRepository.save(category);
        return categoryMapper.toCategoryCreateResponse(category);
    }
    public List<CategoryCreateResponse> getAllCategory() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toCategoryCreateResponse).toList();
    }
}
