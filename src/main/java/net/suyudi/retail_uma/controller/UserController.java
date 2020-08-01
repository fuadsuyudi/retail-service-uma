package net.suyudi.retail_uma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.*;

import net.suyudi.retail_uma.dto.request.LoginRequest;
import net.suyudi.retail_uma.dto.request.UserRegisterRequest;
import net.suyudi.retail_uma.dto.response.BaseResponse;
import net.suyudi.retail_uma.exception.BadRequestException;
import net.suyudi.retail_uma.exception.UnprocessableEntityException;
import net.suyudi.retail_uma.model.User;
import net.suyudi.retail_uma.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.HashMap;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("me")
    public Object returnMe() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getPrincipal();
    }

    @GetMapping()
    public BaseResponse getAllUser() {
        return userService.getListUser();
    }
    
    @PostMapping("register")
    public BaseResponse registerUser(@Valid @RequestBody UserRegisterRequest request) {
        return userService.createNewUser(request);
    }

    @PutMapping("{id}/password")
    public BaseResponse putPassword(@PathVariable Long id, @RequestParam("new") String password) {
        return userService.changePassword(id, password);
    }

    @PostMapping("login")
    public BaseResponse<OAuth2AccessToken> login(@Valid @RequestBody LoginRequest request) {
        HashMap<String, String> params = request.getMap();
        User checkUser = userService.getByUsername(params.get("username"));

        if (checkUser == null) {
            throw new BadRequestException("Invalid Credentials");
        }

        if (checkUser != null && checkUser.getStatus() == 0) {
            throw new UnprocessableEntityException("Your account is inactive, please contact the help center");
        }

        if (checkUser != null && checkUser.getStatus() == 2) {
            throw new UnprocessableEntityException("Your account has been suspended, please contact the help center");
        }

        try {
            OAuth2AccessToken token = userService.getToken(params);
            if (checkUser != null) {
                userService.resetAttempt(checkUser.getId());
            }

            return BaseResponse.ok(token);
        } catch (Exception e) {
            e.printStackTrace();
            if (checkUser != null) {
                if (checkUser.getLoginAttempt() != null && checkUser.getLoginAttempt() == 5) {
                    userService.suspendUserLogin(checkUser.getId());

                    throw new UnprocessableEntityException("Your account has been suspended, please contact the help center");
                } else {
                    userService.updateAttempt(checkUser.getId());

                    throw new BadRequestException("Invalid Credentials");
                }
            }
        }

        throw new BadRequestException("Invalid Credentials");
    }

    @PostMapping("logout")
    public BaseResponse logout(HttpServletRequest request) {
        return userService.logout(request);
    }

}
