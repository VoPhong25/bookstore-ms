package com.example.identity_service.repository.httpclient;

import com.example.identity_service.dto.request.UserProfileCreateRequest;
import com.example.identity_service.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "${app.services.profile}")
public interface UserProfileClient {
     // them internal de chi khi create user thi profile moi dc goi
     @PostMapping(value = "/internal/users", produces = MediaType.APPLICATION_JSON_VALUE)
     UserProfileResponse createUserProfile(@RequestBody UserProfileCreateRequest request);

}
