package org.karl.service.gateway.config;
/**
 * Created by KARL_ROSE on 2020/2/29 10:18
 */

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Map;

/**
 *  AuthFilter
 * @author ROSE
 * @date 2020/2/29 10:18
 *
 **/
@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    private static final String HEAR_AUTH = "Authorization";

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String ip = getIpAddress(request);
        String token = request.getHeaders().getFirst(HEAR_AUTH);
        if (token == null || token.isEmpty()) {
            ServerHttpResponse response = exchange.getResponse();
            Map<Object, Object> map = Maps.newHashMap();
            map.put("code", 401);
            map.put("message", "非法请求！");
            map.put("cause", "Token is null");
            map.put("ip", ip);
            ObjectMapper mapper = new ObjectMapper();
            // 输出错误信息到页面
            DataBuffer buffer = response.bufferFactory().wrap(JSON.toJSONString(map).getBytes());
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            return response.writeWith(Mono.just(buffer));

        }
        return chain.filter(exchange);
    }

    private String getIpAddress(ServerHttpRequest request) {
        //HttpHeaders headers = request.getHeaders();
        String ip;
        try {
            //根据header获取相应校验
            //logger.info(headers.getFirst("Authorization"));
            //List<String> list = headers.get("x-forwarded-for");
            InetSocketAddress address = request.getRemoteAddress();
            assert address != null;
            InetAddress inetAddress = address.getAddress();
            ip = inetAddress.getHostAddress();
            log.info("inetAddress host name :" + inetAddress.getHostName());
        } catch (Exception e) {
            log.error(e.getMessage());
            ip = "";
        }
        return ip;
    }
}
