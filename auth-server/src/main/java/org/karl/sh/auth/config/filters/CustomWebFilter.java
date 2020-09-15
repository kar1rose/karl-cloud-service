//package org.karl.sh.auth.config.filters;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @author ROSE
// * @date 2019/7/5 17:53
// **/
//
//@Order(Ordered.HIGHEST_PRECEDENCE)
//@Configuration
//@WebFilter(filterName = "cuckooWebFilter", urlPatterns = "/*")
//public class CustomWebFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        //设置允许跨域的域名或服务器ip
//        response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:8889");
//        //增加允许的请求方式
//        response.setHeader("Access-Control-Allow-Methods", "POST,GET,DELETE,PUT,OPTIONS");
//        //设置允跨域许的头信息
//        response.setHeader("Access-Control-Allow-Headers", "Authorization,DNT,X-Mx-ReqToken,Keep-Alive,User-Agent,X-Requested-With,X_Requested_With,If-Modified-Since,Cache-Control,Content-Type, Accept-Language, Origin, Accept-Encoding");
//        //response.setHeader("Access-Control-Allow-Credentials", "true");
//        filterChain.doFilter(servletRequest, response);
//
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) {
//
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
