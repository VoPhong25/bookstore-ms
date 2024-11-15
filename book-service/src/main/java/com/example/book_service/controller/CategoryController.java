package com.example.book_service.controller;

import com.example.book_service.dto.ApiResponse;
import com.example.book_service.dto.request.CategoryCreateRequest;
import com.example.book_service.dto.response.CategoryCreateResponse;
import com.example.book_service.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/getAllCategory")
    ApiResponse<List<CategoryCreateResponse>> getAllCategory() {
        return ApiResponse.<List<CategoryCreateResponse>>builder()
               .result(categoryService.getAllCategory())
               .build();
    }
    @DeleteMapping("/deleteCategory/{id}")
    ApiResponse<String> deleteCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);
        return ApiResponse.<String>builder()
               .result("Category has been deleted")
               .build();
    }
    @PutMapping("/updateCategory/{id}")
    ApiResponse<CategoryCreateResponse> updateCategory(@PathVariable int id, @RequestBody CategoryCreateRequest request) {
        return ApiResponse.<CategoryCreateResponse>builder()
               .result(categoryService.updateCategory(id, request))
               .build();
    }
}
