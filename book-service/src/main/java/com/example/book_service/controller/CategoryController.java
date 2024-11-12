package com.example.book_service.controller;

import com.example.book_service.dto.ApiResponse;
import com.example.book_service.dto.request.CategoryCreateRequest;
import com.example.book_service.dto.response.CategoryCreateResponse;
import com.example.book_service.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryController {
    CategoryService categoryService;
    @PostMapping("/createCategory")
    ApiResponse<CategoryCreateResponse> createCategory(@RequestBody CategoryCreateRequest request) {
        return ApiResponse.<CategoryCreateResponse>builder()
               .result(categoryService.createCategory(request))
               .build();
    }
}
