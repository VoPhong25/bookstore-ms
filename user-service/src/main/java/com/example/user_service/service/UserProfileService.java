package com.example.user_service.service;

import com.example.user_service.dto.request.UserProfileCreateRequest;
import com.example.user_service.dto.response.UserProfileResponse;
import com.example.user_service.emtity.UserProfile;
import com.example.user_service.mapper.UserProfileMapper;
import com.example.user_service.repository.UserProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileService {
    UserProfileRepository userProfileRepository;
    UserProfileMapper userProfileMapper;
    public UserProfileResponse createUserProfile(UserProfileCreateRequest request){
        UserProfile userProfile = userProfileMapper.toUserProfile(request);
        userProfile = userProfileRepository.save(userProfile);
        return userProfileMapper.toUserProfileResponse(userProfile);
    }

    public UserProfileResponse getUserProfileByuserId(String userId){
        UserProfile userProfile = userProfileRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("Profile not found"));
        return userProfileMapper.toUserProfileResponse(userProfile);
    }
}
