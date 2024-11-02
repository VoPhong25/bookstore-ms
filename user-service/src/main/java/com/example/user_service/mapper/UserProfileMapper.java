package com.example.user_service.mapper;

import com.example.user_service.dto.request.UserProfileCreateRequest;
import com.example.user_service.dto.response.UserProfileResponse;
import com.example.user_service.emtity.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(UserProfileCreateRequest request);
    UserProfileResponse toUserProfileResponse(UserProfile emtity);
}
