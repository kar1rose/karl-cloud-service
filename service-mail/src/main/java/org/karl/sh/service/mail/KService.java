package org.karl.sh.service.mail;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * @author KARL.ROSE
 * @date 2020/3/24 8:14 下午
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface KService {

    @AliasFor(annotation = Service.class)
    String value() default "";

}
