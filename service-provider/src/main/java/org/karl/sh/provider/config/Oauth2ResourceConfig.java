package org.karl.sh.provider.config;

import org.karl.sh.core.strategy.Oauth2JsonSerializationStrategy;
import org.karl.sh.provider.config.authorization.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author KARL ROSE
 * @date 2020/9/8 16:42
 **/
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class Oauth2ResourceConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //关闭iframe校验
        http.headers().frameOptions().disable();
        //登陆 验证码 swagger接口及js文件
        http.csrf().disable().authorizeRequests()
                .antMatchers("/actuator/**", "/login", "/code").permitAll()
                .antMatchers("/**").authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        //无状态
        resources.stateless(true)
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .tokenStore(tokenStore());
    }

    /**
     * 设置token存储，这一点配置要与授权服务器相一致
     */
    @Bean
    public RedisTokenStore tokenStore() {
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
//        redisTokenStore.setSerializationStrategy(new Oauth2JsonSerializationStrategy());
        redisTokenStore.setPrefix("karl-auth-token:");
        return redisTokenStore;
    }
}
