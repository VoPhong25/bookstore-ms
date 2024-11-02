package com.example.identity_service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileCreateRequest {
     String userId;
     String username;
     String password;
     String firstname;
     String lastname;
     LocalDate birthday;
     String location;
     int phonenumber;

}
