package org.karl.service.gateway.config;


import lombok.SneakyThrows;
import org.apache.http.HttpHeaders;
import org.karl.service.gateway.clients.IAuthClient;
import org.karl.service.gateway.service.IRateLimitService;
import org.karl.sh.core.beans.sys.AuthTokenCheckDto;
import org.karl.sh.core.templates.ApiResult;
import org.karl.sh.core.templates.BaseException;
import org.karl.sh.core.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * AuthFilter
 *
 * @author ROSE
 * @date 2020/2/29 10:18
 **/
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    private static final String HEAR_AUTH = "Authorization";
    private static final String PARAM_AUTH = "access_token";
    private static final String UNKNOWN = "unknown";

    @Autowired
    private IRateLimitService limitService;

    @Autowired
    private IAuthClient authClient;

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String token = achieveToken(request);
        if (StringUtils.isEmpty(token)) {
            return stopAndReturn(ApiResult.error("invalid token"), response);
        }
        try {
            AuthTokenCheckDto dto = authClient.checkToken(token);
            logger.info("用户登录:{}", JsonUtils.convert(dto));
        } catch (BaseException e) {
            return stopAndReturn(ApiResult.error(e.getMessage()), response);
        }
        if (limitService.limited(request.getURI().getPath(), 10)) {
            logger.info("通过");
        } else {
            return stopAndReturn(ApiResult.error("rate limited"), response);
        }


        return chain.filter(exchange);
    }

    private Mono<Void> stopAndReturn(ApiResult<String> result, ServerHttpResponse response) {
        byte[] bits = Objects.requireNonNull(JsonUtils.convert(result)).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        return response.writeWith(Mono.just(buffer));
    }

    public static String getIpAddress(HttpServletRequest request) {
        String flag = ",";
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !UNKNOWN.equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.contains(flag)) {
                ip = ip.split(flag)[0];
            }
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 请求是否被拦截
     * 1.ip地址校验 黑白名单
     * 2.token校验
     *
     * @param request 请求实体
     * @author KARL.ROSE
     * @date 2021/3/17 5:34 下午
     **/
    private String achieveToken(ServerHttpRequest request) {

        InetSocketAddress address = request.getRemoteAddress();
        assert address != null;
        InetAddress inetAddress = address.getAddress();
        String hostAddress = inetAddress.getHostAddress();
//        String ip = getIpAddress((HttpServletRequest) request);
//        logger.info("inetAddress host address :{},come from {}", hostAddress, ip);

        String token = request.getHeaders().getFirst(HEAR_AUTH);
        if (StringUtils.isEmpty(token)) {
            token = request.getQueryParams().getFirst(PARAM_AUTH);
        }
        return token;
    }
}
