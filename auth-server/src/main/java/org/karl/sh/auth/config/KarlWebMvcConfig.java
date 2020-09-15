//package org.karl.sh.auth.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.StringHttpMessageConverter;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
//import java.nio.charset.StandardCharsets;
//
///**
// * @author KARL.ROSE
// * @date 2020/5/18 3:32 下午
// **/
//@Configuration
//public class KarlWebMvcConfig extends WebMvcConfigurationSupport {
//
//    /**
//     * 跨域访问
//     **/
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedHeaders("*")
//                .allowedMethods("*")
//                .allowCredentials(true);
//    }
//
//    /*@Bean
//    public HttpMessageConverter<String> responseBodyConverter() {
//        return new StringHttpMessageConverter(StandardCharsets.UTF_8);
//    }*/
//
//    /**
//     * 注册用户信息拦截器
//     *
//     * @param registry 拦截器注册信息
//     */
//    /*@Override
//    protected void addInterceptors(InterceptorRegistry registry) {
////        registry.addInterceptor(new IpInterceptor()).addPathPatterns("/**");
////        registry.addInterceptor(new UserInfoInterceptor()).addPathPatterns("/**").excludePathPatterns(HttpOpenUrl.URL);
//        super.addInterceptors(registry);
//    }*/
//
//    /*@Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        //解决静态资源访问问题
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//        // 解决swagger无法访问
//        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
//        // 解决swagger的js文件无法访问
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//        super.addResourceHandlers(registry);
//    }*/
//
//}
