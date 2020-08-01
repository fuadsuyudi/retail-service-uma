package net.suyudi.retail_uma.service;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import net.suyudi.retail_uma.dto.request.UserRegisterRequest;
import net.suyudi.retail_uma.dto.response.BaseResponse;
import net.suyudi.retail_uma.model.User;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;

public interface UserService {

    BaseResponse getListUser();
    BaseResponse createNewUser(UserRegisterRequest request);
    User getByUsername(String username);
    User updateAttempt(Long id);
    User resetAttempt(Long id);
    User suspendData(Long id);
    User suspendUserLogin(Long id);
    BaseResponse logout(HttpServletRequest request);
    BaseResponse changePassword(Long id, String password);

    OAuth2AccessToken getToken(HashMap<String, String> params) throws HttpRequestMethodNotSupportedException;
    // OAuth2AccessToken getToken(User user) throws HttpRequestMethodNotSupportedException;

}
