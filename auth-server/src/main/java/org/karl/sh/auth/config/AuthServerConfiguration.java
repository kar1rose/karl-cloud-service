package org.karl.sh.auth.config;

import org.karl.sh.core.utils.PwdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

/**
 * @author KARL.ROSE
 * @date 2020/5/18 2:02 下午
 **/
@Configuration
@EnableAuthorizationServer
public class AuthServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsServiceImpl;

    @Bean
    public JdbcClientDetailsService customClientDetailsService() {
        JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        clientDetailsService.setPasswordEncoder(PwdUtils.ENCODER);
        return clientDetailsService;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(customClientDetailsService());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager).userDetailsService(customUserDetailsServiceImpl).tokenStore(tokenStore());
        //配置TokenService参数
        DefaultTokenServices tokenService = new DefaultTokenServices();
        tokenService.setTokenStore(endpoints.getTokenStore());
        tokenService.setSupportRefreshToken(true);
        tokenService.setClientDetailsService(endpoints.getClientDetailsService());
        tokenService.setTokenEnhancer(endpoints.getTokenEnhancer());
        tokenService.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30));
        tokenService.setRefreshTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(50));
        tokenService.setReuseRefreshToken(false);
        endpoints.tokenServices(tokenService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.tokenKeyAccess("isAuthenticated()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients(); //允许接口/oauth/check_token 被调用
    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }


}
