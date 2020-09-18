package org.karl.sh.auth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * web安全配置
 *
 * @author KARL.ROSE
 * @date 2020/5/18 12:14 下午
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(-1)
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsServiceImpl;

    @Autowired
    private LoginAuthenticationProvider loginAuthenticationProvider;


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.requestMatchers().antMatchers(HttpMethod.OPTIONS, "/oauth/**").and()
                .httpBasic().and()
                .logout().clearAuthentication(true).logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
            Object obj = authentication.getPrincipal();
            log.info(authentication.getName() + "注销。。。{}", obj);
        });
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(loginAuthenticationProvider);
//                .userDetailsService(customUserDetailsServiceImpl)
//                .passwordEncoder(passwordEncoder());
    }
}


