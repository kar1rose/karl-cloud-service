package org.karl.sh.auth.config.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ROSE
 * @date 2019/7/5 17:53
 **/

@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@WebFilter(filterName = "customWebFilter", urlPatterns = "/*")
public class CustomWebFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(CustomWebFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //设置允许跨域的域名或服务器ip
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        //增加允许的请求方式
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST,GET,DELETE,PUT,OPTIONS");
        //设置允跨域许的头信息
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Authorization,Content-Type");
//        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
//        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "1728000");
        filterChain.doFilter(servletRequest, response);

    }

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("===============init()===============");
    }

    @Override
    public void destroy() {
        logger.info("===============destroy()===============");
    }
}
