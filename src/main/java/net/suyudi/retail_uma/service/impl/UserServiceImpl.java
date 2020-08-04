package net.suyudi.retail_uma.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import net.suyudi.retail_uma.config.security.RestConfiguration;
import net.suyudi.retail_uma.dto.request.UserRegisterRequest;
import net.suyudi.retail_uma.dto.response.BaseResponse;
import net.suyudi.retail_uma.exception.*;
import net.suyudi.retail_uma.model.Profile;
import net.suyudi.retail_uma.model.Role;
import net.suyudi.retail_uma.model.User;
import net.suyudi.retail_uma.repository.ProfileRepository;
import net.suyudi.retail_uma.repository.UserRepository;
import net.suyudi.retail_uma.service.RoleService;
import net.suyudi.retail_uma.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Value("${users.access.role.owner}")
    private Integer LEVEL_OWNER;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ClientDetailsService clientDetailsStore;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private DataSource dataSource;

    @Autowired
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Autowired
    public AuthorizationServerTokenServices tokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();

        defaultTokenServices.setTokenStore(tokenStore());

        return defaultTokenServices;
    }

    @Override
    public BaseResponse createNewUser(UserRegisterRequest request) {
        try {
            Role role = roleService.findByLevel(LEVEL_OWNER);
            if (role == null) {
                throw new InternalServerErrorException();
            }

            if (!checkByUsername(request.getUsername())) {
                throw new UnprocessableEntityException("Username already exist");
            }

            if (!checkByMobile(request.getMobile())) {
                throw new UnprocessableEntityException("Mobile already exist");
            }

            User user = new User();

            user.setRole(role);
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setMobile(request.getMobile());
            user.setLoginAttempt(0);
            user.setCreatedAt(new Date());
            user.setIsDelete(0);
            user.setStatus(1);

            userRepository.save(user);

            Profile profile = new Profile();

            profile.setUser(user);
            profile.setFirstName(request.getFirstName());
            profile.setLastName(request.getLastName());
            profile.setJoinAt(new Date());
            profile.setCreatedAt(new Date());
            profile.setIsDelete(0);

            profileRepository.save(profile);

            return BaseResponse.created(user);
        } catch (Exception e) {
            e.getMessage();
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public User getByUsername(String username) {
        try {
            return userRepository.getUserByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public BaseResponse getListUser() {
        try {
            return BaseResponse.ok(userRepository.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User updateAttempt(Long id) {
        try {
            User user = userRepository.getOne(id);
            if (user != null) {
                if (user.getLoginAttempt() == null) {
                    user.setLoginAttempt(0);
                }

                user.setLoginAttempt(user.getLoginAttempt() + 1);
                user.setUpdatedAt(new Date());
                user.setLastLoginAttempt(new Date());
                userRepository.save(user);

                return null;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User resetAttempt(Long id) {
        try {
            User user = userRepository.getOne(id);
            if (user != null) {
                user.setLoginAttempt(0);
                user.setStatus(1);
                user.setUpdatedAt(new Date());
                userRepository.save(user);

                return null;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User suspendData(Long id) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User userSession = (User) auth.getPrincipal();

            // if (userSession.getRole().getId() != 1) {
            // // return BaseResponse.error("99", "Hanya Role SYSADMIN yang dapat Suspend
            // Data User");
            // }

            User user = userRepository.getOne(id);
            if (user != null) {
                user.setStatus(2); // suspend
                user.setUpdatedAt(new Date());
                userRepository.save(user);

                return null;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public OAuth2AccessToken getToken(HashMap<String, String> params) throws HttpRequestMethodNotSupportedException {
        params.put("client_id", RestConfiguration.CLIENT_ID);
        params.put("client_secret", RestConfiguration.CLIENT_SECRET);
        params.put("grant_type", "password");

        DefaultOAuth2RequestFactory defaultOAuth2RequestFactory = new DefaultOAuth2RequestFactory(clientDetailsStore);
        AuthorizationRequest authorizationRequest = defaultOAuth2RequestFactory.createAuthorizationRequest(params);
        authorizationRequest.setApproved(true);

        OAuth2Request oauth2Request = defaultOAuth2RequestFactory.createOAuth2Request(authorizationRequest);
        UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(params.get("username"),
                params.get("password"));
        Authentication authentication = authenticationManager.authenticate(loginToken);

        OAuth2Authentication authenticationRequest = new OAuth2Authentication(oauth2Request, authentication);
        authenticationRequest.setAuthenticated(true);

        OAuth2AccessToken token = tokenServices().createAccessToken(authenticationRequest);

        Map<String, Object> adInfo = new HashMap<>();
        // adInfo.put("refresh_token", null);
        adInfo.put("role", null);

        try {
            User user = (User) authentication.getPrincipal();
            // adInfo.put("refresh_token", token.getRefreshToken());
            adInfo.put("role", user.getRole());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ((DefaultOAuth2AccessToken) token).setAdditionalInformation(adInfo);

        return token;
    }

    // @Override
    // public OAuth2AccessToken getToken(User user) throws
    // HttpRequestMethodNotSupportedException {
    // HashMap<String, String> params = new HashMap<String, String>();

    // params.put("client_id", RestConfiguration.CLIENT_ID);
    // params.put("client_secret", RestConfiguration.CLIENT_SECRET);
    // params.put("grant_type", "password");
    // params.put("username", user.getUsername());
    // params.put("password", user.getPassword());

    // DefaultOAuth2RequestFactory defaultOAuth2RequestFactory = new
    // DefaultOAuth2RequestFactory(clientDetailsStore);
    // AuthorizationRequest authorizationRequest =
    // defaultOAuth2RequestFactory.createAuthorizationRequest(params);
    // authorizationRequest.setApproved(true);

    // OAuth2Request oauth2Request =
    // defaultOAuth2RequestFactory.createOAuth2Request(authorizationRequest);
    // UsernamePasswordAuthenticationToken loginToken = new
    // UsernamePasswordAuthenticationToken(user, null, null); //
    // user.getAuthorities()

    // OAuth2Authentication authenticationRequest = new
    // OAuth2Authentication(oauth2Request, loginToken);
    // authenticationRequest.setAuthenticated(true);

    // OAuth2AccessToken token =
    // tokenServices().createAccessToken(authenticationRequest);

    // Map<String, Object> adInfo = new HashMap<>();

    // adInfo.put("role", null);

    // try {
    // adInfo.put("role", user.getRole());
    // } catch (Exception e) {
    // e.printStackTrace();
    // }

    // ((DefaultOAuth2AccessToken) token).setAdditionalInformation(adInfo);

    // return token;
    // }

    @Override
    public User suspendUserLogin(Long id) {
        try {
            User user = userRepository.getOne(id);
            if (user != null) {
                user.setStatus(2); // suspend
                user.setUpdatedAt(new Date());
                user.setLastLoginAttempt(new Date());
                userRepository.save(user);

                return null;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public BaseResponse logout(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null) {
                String tokenValue = authHeader.replace("Bearer", "").trim();
                OAuth2AccessToken accessToken = tokenStore().readAccessToken(tokenValue);
                tokenStore().removeAccessToken(accessToken);

                return BaseResponse.ok();
            } else {
                return BaseResponse.error("99", "Session Invalid");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.error("99", e.getMessage());
        }
    }

    private boolean checkByUsername(String username) {
        try {
            User chkByUsername = userRepository.getUserByUsername(username);
            if (chkByUsername == null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkByMobile(String mobile) {
        try {
            User chkByMobile = userRepository.getUserByMobile(mobile);
            if (chkByMobile == null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public BaseResponse changePassword(Long id, String password) {
        User user = userRepository.findById(id).orElseThrow(() -> new UnprocessableEntityException());

        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);

        return BaseResponse.ok(user);
    }

}