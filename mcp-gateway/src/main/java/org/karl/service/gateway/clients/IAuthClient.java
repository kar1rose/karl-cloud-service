package org.karl.service.gateway.clients;

import org.karl.service.gateway.config.AuthCheckDecoder;
import org.karl.sh.core.beans.sys.AuthTokenCheckDto;
import org.karl.sh.core.constants.AppNames;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * auth权限服务feign客户端
 *
 * @author KARL.ROSE
 * @date 2021/4/2 10:34
 */
@FeignClient(value = AppNames.AUTHORIZATION_SERVER, configuration = {AuthCheckDecoder.class})
public interface IAuthClient {


    /**
     * 检查token是否有效
     *
     * @param token 待检查的token
     * @return 封装实体bean
     * @author KARL.ROSE
     * @date 2021/4/2 10:40
     */
    @GetMapping(value = "/oauth/check_token")
    AuthTokenCheckDto checkToken(@RequestParam("token") String token);
}
