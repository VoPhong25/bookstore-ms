package com.example.book_service.service;

import com.example.book_service.dto.request.BookCreateRequest;
import com.example.book_service.dto.response.BookCreateResponse;
import com.example.book_service.emtity.Book;
import com.example.book_service.emtity.Category;
import com.example.book_service.exception.AppException;
import com.example.book_service.exception.ErrorCode;
import com.example.book_service.mapper.BookMapper;
import com.example.book_service.repository.BookRepository;
import com.example.book_service.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookService {
    BookRepository bookRepository;
    CategoryRepository categoryRepository;
    BookMapper bookMapper;
    public BookCreateResponse addBook(BookCreateRequest request, MultipartFile file) throws IOException {

        // Tạo đối tượng sách và lưu vào cơ sở dữ liệu
        Book book = bookMapper.toBook(request);

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(()
                -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        String imageUrl = saveImage(file);
        book.setImageUrl(imageUrl);
        bookRepository.save(book);
        return bookMapper.toBookCreateResponse(book);
    }

    // Phương thức lưu ảnh
    private String saveImage(MultipartFile file) throws IOException {
        // Đường dẫn lưu ảnh
        String uploadDir = "uploads"; // Sử dụng đường dẫn tương đối

        // Tạo thư mục nếu chưa tồn tại
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath); // Tạo thư mục
        }

        // Tạo tên file duy nhất
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // Đường dẫn đầy đủ của file
        Path filePath = uploadPath.resolve(fileName);

        // Ghi dữ liệu file vào đường dẫn
        Files.write(filePath, file.getBytes());

        // Trả về đường dẫn file để lưu trong cơ sở dữ liệu
        return "/" + uploadDir + "/" + fileName;
    }

}


