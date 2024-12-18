package com.example.user_service.controller;

import com.example.user_service.dto.request.UserProfileCreateRequest;
import com.example.user_service.dto.response.UserProfileResponse;
import com.example.user_service.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InternalUserProfileController {
    UserProfileService userProfileService;
    @PostMapping("/internal/users")
    UserProfileResponse createUserProfile(@RequestBody UserProfileCreateRequest request){
        return userProfileService.createUserProfile(request);
    }

}
