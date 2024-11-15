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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
                .result(bookService.addBook(file, request))
                .build();
    }
    @GetMapping("/getAllBook")
     ApiResponse<List<BookCreateResponse>> getAllBook() {
        return ApiResponse.<List<BookCreateResponse>>builder()
                .result(bookService.getAllBook())
               .build();
     }
     @GetMapping("/getBookById/{id}")
     ApiResponse<BookCreateResponse> getBookById(@PathVariable("id") int id) {
        return ApiResponse.<BookCreateResponse>builder()
               .result(bookService.getBookById(id))
               .build();
     }
     @GetMapping("/getBookByCategoryId/{categoryId}")
     ApiResponse<List<BookCreateResponse>> getBookByCategoryId(
             @PathVariable("categoryId") int categoryId) {
        return ApiResponse.<List<BookCreateResponse>>builder()
                .result(bookService.getBookByCategoryId(categoryId))
               .build();
     }
     @DeleteMapping("/deleteBook/{id}")
     ApiResponse<String> deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return ApiResponse.<String>builder()
               .result("Book has been deleted")
               .build();
     }

    @PutMapping("/updateBook/{id}")
    ApiResponse<BookCreateResponse> updateBook(
            @PathVariable("id") int bookId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("request") String requestJson) throws IOException {
        // Chuyển JSON thành đối tượng BookCreateRequest
        ObjectMapper objectMapper = new ObjectMapper();
        BookCreateRequest request = objectMapper.readValue(requestJson, BookCreateRequest.class);

        return ApiResponse.<BookCreateResponse>builder()
                .result(bookService.updateBook(bookId, file, request))
                .build();
    }
}
