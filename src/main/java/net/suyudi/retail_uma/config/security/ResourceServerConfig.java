package net.suyudi.retail_uma.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

import net.suyudi.retail_uma.config.ErrorExceptionHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Bean
	ErrorExceptionHandler accessDeniedHandler() {
		return new ErrorExceptionHandler();
	}

	@Bean
	ErrorExceptionHandler authenticationEntryPoint() {
		return new ErrorExceptionHandler();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RestConfiguration.RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                .antMatchers(
                    "/oauth/authorize",
                    "/api/oauth/authorize",
                    "/oauth/check_token",
                    "/uma/oauth/check_token",
                    "/users/login",
                    "/users/register",
                    // "/roles/**",
                    "/oauth/**",
                    "/password/**",
                    "/test/check",
                    // "/users/**",
                    "/view-data/**",
                    "/v2/api-docs",
                    "/configuration/ui",
                    "/swagger-resources/**",
                    "/configuration/security",
                    "/swagger-ui.html",
                    "/webjars/**"
                    ).permitAll()
            .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint())
            .and()
                .authorizeRequests()
                .anyRequest()
                .access("isAuthenticated()")
            .and()
                .exceptionHandling()
                .accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }

}
