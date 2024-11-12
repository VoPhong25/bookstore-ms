package com.example.identity_service.service;

import com.example.event.dto.NotificationEvent;
import com.example.identity_service.dto.request.UserCreatRequest;
import com.example.identity_service.dto.request.UserUpdateRequest;
import com.example.identity_service.dto.response.UserResponse;
import com.example.identity_service.entity.User;
import com.example.identity_service.enums.Role;
import com.example.identity_service.exception.AppException;
import com.example.identity_service.exception.ErrorCode;
import com.example.identity_service.mapper.ProfileMapper;
import com.example.identity_service.mapper.UserMapper;
import com.example.identity_service.repository.UserRepository;
import com.example.identity_service.repository.httpclient.UserProfileClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    ProfileMapper profileMapper;
    PasswordEncoder passwordEncoder;
    UserProfileClient userProfileClient;
    KafkaTemplate<String, Object> kafkaTemplate;
    public UserResponse createUser(UserCreatRequest request) {

        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);
        user = userRepository.save(user);

        var userProfileRequest = profileMapper.toUserProfileCreateRequest(request);
        userProfileRequest.setUserId(user.getId());
        userProfileRequest.setPassword(user.getPassword());
        userProfileClient.createUserProfile(userProfileRequest);

        NotificationEvent notificationEvent = NotificationEvent.builder()
                .channel("Email")
                .recipient(request.getEmail())
                .subject("Welcome to BookstoreVP")
                .body("Hello, " + request.getUsername() )
                .build();
//      gui mail bang kafka
        kafkaTemplate.send("notification-delivery", notificationEvent);
        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }
    // lay ra thong tin cua user dang login
    public UserResponse getMyInfo() {
        SecurityContext context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN')") /* anotatinon dùng de kiem tra xem nguoi dung
    su dung method nay co ROLE la ADMIN khong */
    public List<UserResponse> getAllUser() {
        log.info("in mothod getAllUser.");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }
    @PostAuthorize("returnObject.username==authentication.name")
    public UserResponse getUserById(String Id) {
        log.info("in method get User by Id");
        return userMapper.toUserResponse(userRepository.findById(Id).orElseThrow(() -> new RuntimeException("User not found")));
    }
    public void deleteUser(String Id) {
        userRepository.deleteById(Id);
    }

}
