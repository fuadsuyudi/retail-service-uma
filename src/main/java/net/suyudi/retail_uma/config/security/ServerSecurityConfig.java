package net.suyudi.retail_uma.config.security;

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
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class ServerSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Autowired
    // private BCryptPasswordEncoder passwordEncoder;

    // @Bean
    // public PasswordEncoder userPasswordEncoder() {
    //     return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    // }

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
    //     http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
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

//    public AuthFilter authenticationFilter() throws Exception {
//        AuthFilter filter = new AuthFilter();
//        filter.setAuthenticationManager(authenticationManagerBean());
////        filter.setAuthenticationFailureHandler(failureHandler());
//        return filter;
//    }

}
