package net.suyudi.retail_uma.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.suyudi.retail_uma.dto.request.UserProfileRequest;
import net.suyudi.retail_uma.dto.response.BaseResponse;
import net.suyudi.retail_uma.service.ProfileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("{id}/profile")
    public BaseResponse getProfile(@PathVariable Long id) {
        return profileService.getProfile(id);
    }

    @PostMapping("{id}/profile")
    public BaseResponse postProfile(@PathVariable Long id, @Valid @RequestBody UserProfileRequest request) {
        return profileService.update(id, request);
    }
    
}