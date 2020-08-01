package net.suyudi.retail_uma.service;

import net.suyudi.retail_uma.dto.request.UserProfileRequest;
import net.suyudi.retail_uma.dto.response.BaseResponse;
import net.suyudi.retail_uma.model.Profile;

public interface ProfileService {

	public BaseResponse getProfile(Long id);

	public BaseResponse update(Long id, UserProfileRequest profile);

}