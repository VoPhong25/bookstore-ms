package com.example.book_service.mapper;

import com.example.book_service.dto.request.BookCreateRequest;
import com.example.book_service.dto.response.BookCreateResponse;
import com.example.book_service.emtity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book toBook(BookCreateRequest request);
    BookCreateResponse toBookCreateResponse(Book book);

    void updateBook(@MappingTarget Book book, BookCreateRequest request);
}
