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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookService {
    BookRepository bookRepository;
    CategoryRepository categoryRepository;
    BookMapper bookMapper;
    public BookCreateResponse addBook(MultipartFile file, BookCreateRequest request) throws IOException {
        // Tạo đối tượng sách và lưu vào cơ sở dữ liệu
        Book book = bookMapper.toBook(request);
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        String imageUrl = saveImage(file);
        book.setImageUrl(imageUrl);
        bookRepository.save(book);
        return bookMapper.toBookCreateResponse(book);
    }
    public List<BookCreateResponse> getAllBook() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toBookCreateResponse).toList();
    }

    public BookCreateResponse getBookById(int id) {
        return bookMapper.toBookCreateResponse(bookRepository.findById(String.valueOf(id)).orElseThrow(()
                -> new AppException(ErrorCode.BOOK_NOT_FOUND)));
    }
    public List<BookCreateResponse> getBookByCategoryId(int categoryId){
        return bookRepository.findAllByCategoryId(categoryId).stream()
               .map(bookMapper::toBookCreateResponse).toList();
    }
    public void deleteBook(int id) {
        bookRepository.deleteById(String.valueOf(id));
    }
    public BookCreateResponse updateBook(int bookId, MultipartFile file, BookCreateRequest request) throws IOException {
        // Tìm sách theo ID
        Book book = bookRepository.findById(String.valueOf(bookId))
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));

        if (request.getCategoryId() > 0) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        }
        bookMapper.updateBook(book, request);


            if (file != null && !file.isEmpty()) {
                deleteImg(book.getImageUrl());

                String imageUrl = saveImage(file);
                book.setImageUrl(imageUrl);
            }

        return bookMapper.toBookCreateResponse(bookRepository.save(book));
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
    private void deleteImg(String imagePath) {
        // Kiểm tra nếu imagePath không null và không rỗng
        if (imagePath != null && !imagePath.isEmpty()) {
            // Xóa dấu "/" đầu tiên để lấy tên file
            String fileName = imagePath.substring(1);  // Bỏ dấu "/" đầu tiên

            // Đường dẫn đầy đủ tới ảnh trong thư mục uploads
            Path fileToDeletePath = Paths.get(fileName);
            File fileToDelete = fileToDeletePath.toFile();

            // Kiểm tra nếu file tồn tại và xóa
            if (fileToDelete.exists()) {
                boolean deleted = fileToDelete.delete();  // Xóa file
                if (deleted) {
                    System.out.println("Đã xóa ảnh: " + fileToDeletePath.toString());
                } else {
                    System.out.println("Không thể xóa ảnh: " + fileToDeletePath.toString());
                }
            } else {
                System.out.println("Ảnh không tồn tại: " + fileToDeletePath.toString());
            }
        } else {
            System.out.println("Đường dẫn ảnh không hợp lệ: " + imagePath);
        }
    }

}







