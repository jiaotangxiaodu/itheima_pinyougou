package com.itheima.pinyougou.cache.aspect;

import com.itheima.pinyougou.cache.annotation.UseCache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class RedisCacheAspect {

    @Autowired
    private RedisTemplate redisTemplate;


    public Object useCache(ProceedingJoinPoint joinPoint) throws Throwable {
        String cacheKey = getCacheKey(joinPoint);
        Object cache = redisTemplate.boundValueOps(cacheKey).get();
        if (cache != null) {
            return cache;
        } else {
            Object result = joinPoint.proceed(joinPoint.getArgs());
            if (result != null){
                UseCache useCache = getAnnotation(joinPoint, UseCache.class);
                redisTemplate.boundValueOps(cacheKey).set(result,5, TimeUnit.MINUTES);
            }
            return result;
        }
    }
    @Before("@annotation(com.itheima.pinyougou.cache.annotation.ClearCache)")
    public void clearCache(ProceedingJoinPoint joinPoint) throws Throwable {
        String cacheKey = getCacheKey(joinPoint);
        redisTemplate.delete(cacheKey);


    }

    /**
     * 根据类名、方法名和参数值获取唯一的缓存键
     */
    private String getCacheKey(ProceedingJoinPoint joinPoint) {
        return new StringBuilder().append(joinPoint.getSignature().toLongString()).append("&").append(Arrays.toString(joinPoint.getArgs())).toString();
    }

    private <T extends Annotation> T getAnnotation(ProceedingJoinPoint jp, Class<T> clazz) {
        MethodSignature sign = (MethodSignature) jp.getSignature();
        Method method = sign.getMethod();
        return method.getAnnotation(clazz);
    }

}
