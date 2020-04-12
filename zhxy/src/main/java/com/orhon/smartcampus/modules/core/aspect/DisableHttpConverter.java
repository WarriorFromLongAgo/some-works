package com.orhon.smartcampus.modules.core.aspect;


import com.orhon.smartcampus.modules.core.annotation.DisableConverter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class DisableHttpConverter {

//    @Pointcut("@annotation(com.orhon.smartcampus.modules.core.annotation.DisableConverter)")
//    public void disablePointCut() {
//
//    }
//
//    @Around("disablePointCut()")
//    public Object around(ProceedingJoinPoint point) throws Throwable {
//
//
//        MethodSignature signature = (MethodSignature) point.getSignature();
//        Method method = signature.getMethod();
//
//        //DataSource ds = method.getAnnotation(DataSource.class);
//
//        DisableConverter ds = method.getAnnotation(DisableConverter.class);
//        try {
//            return point.proceed();
//        } finally {
//            //DynamicDataSource.clearDataSource();
//            //logger.debug("clean datasource");
//        }
//
//
//    }
}
