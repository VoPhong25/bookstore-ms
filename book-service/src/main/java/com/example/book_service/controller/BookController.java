package com.example.book_service.controller;

import com.example.book_service.dto.ApiResponse;
import com.example.book_service.dto.request.BookCreateRequest;
import com.example.book_service.dto.response.BookCreateResponse;
import com.example.book_service.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookController {
    BookService bookService;
//    @PostMapping("/addBook")
//    ApiResponse<BookCreateResponse> addBook(@RequestParam("file") MultipartFile file,
//                                               @RequestBody BookCreateRequest request) throws IOException {
//        return ApiResponse.<BookCreateResponse>builder()
//               .result(bookService.addBook(request, file))
//               .build();
//    }
@PostMapping(value = "/addBook", consumes = "multipart/form-data")
public ApiResponse<BookCreateResponse> addBook(
        @RequestParam("file") MultipartFile file,
        @RequestParam("request") String requestJson) throws IOException {
    // Chuyển JSON thành đối tượng BookCreateRequest
    ObjectMapper objectMapper = new ObjectMapper();
    BookCreateRequest request = objectMapper.readValue(requestJson, BookCreateRequest.class);

    return ApiResponse.<BookCreateResponse>builder()
            .result(bookService.addBook(request, file))
            .build();
}

}
