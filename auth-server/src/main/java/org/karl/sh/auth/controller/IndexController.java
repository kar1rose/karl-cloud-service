package org.karl.sh.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.karl.sh.core.templates.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author KARL ROSE
 * @date 2020/9/18 17:07
 **/
@RestController
@Slf4j
public class IndexController {

    @Autowired
    private TokenStore tokenStore;

    @PostMapping("/logout")
    public ApiResult<String> logout(HttpServletRequest request) {
        String token = request.getParameter(OAuth2AccessToken.ACCESS_TOKEN);
        if (StringUtils.isEmpty(token)) {
            token = request.getHeader(HttpHeaders.AUTHORIZATION);
        }
        Assert.notNull(token, "无效认证");
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
        if (accessToken != null) {
            tokenStore.removeAccessToken(accessToken);
            tokenStore.removeRefreshToken(accessToken.getRefreshToken());
        }
        return ApiResult.success();
    }
}
