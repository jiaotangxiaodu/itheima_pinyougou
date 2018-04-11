package com.itheima.pinyougou.cache.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.METHOD)
public @interface UseCache {
    long alive() default 1800000;
}
