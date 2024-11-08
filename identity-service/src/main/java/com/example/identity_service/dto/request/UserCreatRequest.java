package com.example.identity_service.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreatRequest {
     String username;
    @Size(min = 8, message = "INVALID_PASSWORD")
     String password;
     String firstname;
     String email;
     String lastname;
     LocalDate birthday;

}
