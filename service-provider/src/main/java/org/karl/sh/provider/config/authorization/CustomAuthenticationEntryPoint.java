package org.karl.sh.provider.config.authorization;

import com.alibaba.fastjson.JSON;
import org.karl.sh.core.templates.API_RESULT_CODE;
import org.karl.sh.core.templates.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.error.AbstractOAuth2SecurityExceptionHandler;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token无效 自定义返回
 *
 * @author KARL.ROSE
 * @date 2020/5/18 3:29 下午
 **/
public class CustomAuthenticationEntryPoint extends AbstractOAuth2SecurityExceptionHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(JSON.toJSONString(ApiResult.error("invalid token")));
    }

}
