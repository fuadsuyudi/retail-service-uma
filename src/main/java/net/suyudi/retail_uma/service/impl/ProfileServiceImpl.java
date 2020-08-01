package net.suyudi.retail_uma.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.suyudi.retail_uma.dto.request.UserProfileRequest;
import net.suyudi.retail_uma.dto.response.BaseResponse;
import net.suyudi.retail_uma.model.Profile;
import net.suyudi.retail_uma.repository.ProfileRepository;
import net.suyudi.retail_uma.service.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public BaseResponse getProfile(Long id) { 
        Profile profile = profileRepository.findByUser(id);

        return BaseResponse.ok(profile);
    }

    @Override
    public BaseResponse update(Long id, UserProfileRequest data) {
        Profile profile = profileRepository.findByUser(id);

        profile.setEmail(data.getEmail());
        profile.setAddress(data.getAddress());

        profileRepository.save(profile);

        return BaseResponse.ok(profile);
    }

}