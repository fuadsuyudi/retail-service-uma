package net.suyudi.retail_uma.config.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class ServerSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .authenticationProvider(authenticationProvider());
    }

    // @Override
    // protected void configure(HttpSecurity http) throws Exception {
    //     http.exceptionHandling()
    //         .accessDeniedHandler(restAccessDeniedHandler)
    //         .authenticationEntryPoint(restAuthenticationEntryPoint);
    //     http.cors().and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    //         .and()
    //             .authorizeRequests()
    //             .antMatchers(
    //                 "/oauth/authorize",
    //                 "/oauth/check_token",
    //                 "/uma/oauth/check_token",
    //                 "/users/login",
    //                 "/users/register",
    //                 // "/roles/**",
    //                 "/oauth/**",
    //                 "/password/**",
    //                 "/test/check",
    //                 "/users/**",
    //                 "/view-data/**",
    //                 "/v2/api-docs",
    //                 "/configuration/ui",
    //                 "/swagger-resources/**",
    //                 "/configuration/security",
    //                 "/swagger-ui.html",
    //                 "/webjars/**"
    //                 ).permitAll()
    //         .anyRequest()
    //         .access("isAuthenticated()");

    // }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    public SimpleUrlAuthenticationFailureHandler failureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/login?error=true");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS", "PUT"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

//    public AuthFilter authenticationFilter() throws Exception {
//        AuthFilter filter = new AuthFilter();
//        filter.setAuthenticationManager(authenticationManagerBean());
////        filter.setAuthenticationFailureHandler(failureHandler());
//        return filter;
//    }

}
