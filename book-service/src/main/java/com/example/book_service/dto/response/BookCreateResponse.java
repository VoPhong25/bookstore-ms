package com.example.book_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookCreateResponse {
    int id;
    String name;
    String author;
    BigDecimal price;
    String description;
    String imageUrl;
    String publisher;
    int categoryId;
}
