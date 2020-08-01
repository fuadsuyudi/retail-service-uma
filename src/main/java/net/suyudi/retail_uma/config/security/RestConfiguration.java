package net.suyudi.retail_uma.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
@EnableGlobalMethodSecurity(prePostEnabled =  true)
@Import(ServerSecurityConfig.class)
public class RestConfiguration extends AuthorizationServerConfigurerAdapter {

    public static final String RESOURCE_ID = "retail-resource-id";
    public static final String CLIENT_ID = "retail-client-id";
    public static final String CLIENT_SECRET = "retail-client-secret";

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // @Autowired
    // private PasswordEncoder oauthClientPasswordEncoder;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    public OAuth2AccessDeniedHandler oauthAccessDeniedHandler() {
        return new OAuth2AccessDeniedHandler();
    }

    @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();

        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setAccessTokenValiditySeconds(72000);
        defaultTokenServices.setRefreshTokenValiditySeconds(78000);

        return defaultTokenServices;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .passwordEncoder(passwordEncoder);
    }

    /**
     * Configure to use Database
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // clients.jdbc(dataSource);
        clients.inMemory()
                .withClient(CLIENT_ID)
                .resourceIds(RESOURCE_ID)
                .secret("{noop}" + CLIENT_SECRET)
                .authorizedGrantTypes("password", "refresh_token", "authorization_code", "implicit")
                .authorities("USER")
                .scopes("read", "write", "trust");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                // .pathMapping("/oauth/authorize", "/uma/oauth/authorize")
                // .pathMapping("/oauth/token", "/uma/oauth/token")
                // .pathMapping("/oauth/check_token", "/uma/oauth/check_token")
                .tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }

    // @Bean
    // protected ResourceServerConfiguration cmsResources() {
    //     ResourceServerConfiguration resource = new ResourceServerConfiguration() {
    //         public void setConfigurers(List<ResourceServerConfigurer> configurers) {
    //             super.setConfigurers(configurers);
    //         }
    //     };
    //     resource.setConfigurers(Arrays.<ResourceServerConfigurer> asList(new ResourceServerConfigurerAdapter() {
    //         @Override
    //         public void configure(ResourceServerSecurityConfigurer resources) {
    //             resources.tokenStore(tokenStore()).resourceId(RESOURCE_ID).stateless(false);;
    //         }

    //         @Override
    //         public void configure(HttpSecurity http) throws Exception {
    //             handleSecurity(http);
    //         }
    //     }));
    //     resource.setOrder(3);
    //     return resource;
    // }

    // private void handleSecurity(HttpSecurity http) throws Exception {
    //     http
    //     .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    //     .and()
    //         .authorizeRequests()
    //         .antMatchers(
    //             "/oauth/authorize",
    //             "/oauth/check_token",
    //             "/uma/oauth/check_token",
    //             "/users/login",
    //             "/users/register",
    //             // "/roles/**",
    //             "/oauth/**",
    //             "/password/**",
    //             "/test/check",
    //             "/users/**",
    //             "/view-data/**",
    //             "/v2/api-docs",
    //             "/configuration/ui",
    //             "/swagger-resources/**",
    //             "/configuration/security",
    //             "/swagger-ui.html",
    //             "/webjars/**"
    //             ).permitAll()
    //     .and()
    //     .authorizeRequests()
    //     .anyRequest()
    //     // .permitAll()
    //     .access("isAuthenticated()")
    //     .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    // }
}
