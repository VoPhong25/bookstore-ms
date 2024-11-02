package com.example.identity_service.mapper;

import com.example.identity_service.dto.request.UserCreatRequest;
import com.example.identity_service.dto.request.UserProfileCreateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    UserProfileCreateRequest toUserProfileCreateRequest(UserCreatRequest request);
}
