package com.catstore.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author
 * @Title:
 * @Package
 * @Description:
 * @date 2021/9/20 8:59
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Context {
    @AliasFor(
            annotation = Component.class
    )
    String value() default "";
}
