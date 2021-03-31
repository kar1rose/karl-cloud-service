package org.karl.sh.warehouse.annotations;

import java.lang.annotation.*;

/**
 * Created by KARL_ROSE on 2020/3/13 21:49
 *
 * @author arvato
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SubmitLock {

    String key() default "LOCK";

}
