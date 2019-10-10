//package com.info.common.aspect;
//
//import com.info.date.DateUtils;
//import com.info.json.JsonUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * 功能描述: 控制层拦截，记录请求的开始，跟请求的结束
// *
// * @Params: * @param null
// * @Author: Gaosx By User
// * @Date: 2019/7/22 17:19
// * @Return:
// */
//@Component
//@Aspect
//public class ControllerAspect {
//
//    private final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);
//
//    @Pointcut("execution(* com.info.modules.*.*Controller.*(..))")
//    private void controllerPointCut() {
//    }
//
//    @Around("execution(* com.info.modules.*.*Controller.*(..))")
//    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
//        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
//        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
//        HttpServletRequest request = sra.getRequest();
//        String url = request.getRequestURL().toString();
//        String method = request.getMethod();
//        String uri = request.getRequestURI();
//        MethodSignature signature = (MethodSignature) pjp.getSignature();
//        String[] parameters = signature.getParameterNames();
//        logger.info("请求开始, 各个参数, url: {}, method: {}, uri: {},", url, method, uri);
//        logger.info("@Before：目标方法为：" + pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName());
//        for (int i = 0; i < pjp.getArgs().length; i++) {
//            logger.info("各个参数,及其值" + (parameters[i] + ":" + pjp.getArgs()[i]));
//        }
//        long time1 = DateUtils.now().getTime();
//        logger.info("-----------------------------------请求开始time：{}-----------------------------------", time1);
//        Object result = null;
//        try {
//            result = pjp.proceed();
//            if (result != null) {
//                logger.info("请求结束，controller的返回值是： " + JsonUtils.obj2JsonStr(result));
//            }
//        } catch (Exception e) {
//            logger.error("请求结束，controller的返回值获取失败：", e);
//            logger.error("目标方法为：" + pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName());
//        }
//        // result的值就是被拦截方法的返回值
//        long time2 = DateUtils.now().getTime();
//        logger.info("--------------请求结束time：{}总耗时overTime：{}-----------------------------------", time2, time2 - time1);
//        return result;
//    }
//}
