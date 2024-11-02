package com.example.user_service.emtity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfile {
    @Id
    String userId;
    String username;
    String password;
    String firstname;
    String lastname;
    LocalDate birthday;
    String location;
    int phonenumber;
}
