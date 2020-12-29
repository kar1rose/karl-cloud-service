package org.karl.sh.auth.config;

import com.alibaba.fastjson.JSON;
import org.karl.sh.core.templates.API_RESULT_CODE;
import org.karl.sh.core.templates.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * web安全配置
 *
 * @author KARL.ROSE
 * @date 2020/5/18 12:14 下午
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsServiceImpl;

    @Autowired
    private LoginAuthenticationProvider loginAuthenticationProvider;

    @Autowired
    private TokenStore tokenStore;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/oauth/**").permitAll()
                .antMatchers("/**").authenticated().and()
                .logout()
                .addLogoutHandler((request, response, authentication) -> {
                    String token = request.getParameter(OAuth2AccessToken.ACCESS_TOKEN);
                    if (StringUtils.isEmpty(token)) {
                        token = request.getHeader(HttpHeaders.AUTHORIZATION);
                    }
                    try {
                        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                        if (StringUtils.isEmpty(token)) {
                            response.getWriter().write(JSON.toJSONString(ApiResult.error(API_RESULT_CODE.INVALID_TOKEN)));
                        } else {
                            OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
                            if (accessToken != null) {
                                tokenStore.removeAccessToken(accessToken);
                                tokenStore.removeRefreshToken(accessToken.getRefreshToken());
                            }
                            response.getWriter().write(JSON.toJSONString(ApiResult.success()));
                        }
                        response.setStatus(HttpStatus.OK.value());
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }
                }).clearAuthentication(true)
                .logoutSuccessHandler((httpServletRequest, response, authentication) -> {
                    log.info("注销成功");
                }).and().httpBasic();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(loginAuthenticationProvider)
                .userDetailsService(customUserDetailsServiceImpl)
                .passwordEncoder(passwordEncoder());
    }
}


