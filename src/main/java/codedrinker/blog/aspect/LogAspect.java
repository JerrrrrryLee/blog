package codedrinker.blog.aspect;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("execution(* codedrinker.blog.controller.*.*(..))")
    public void controllerLog(){ }//签名，可以理解成这个切入点的一个名称


    @Before("controllerLog()")
    //在切入点的方法run之前要干的
    public void doBefore(JoinPoint joinPoint){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();//这个RequestContextHolder是Springmvc提供来获得请求的东西
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
        RequestLog requestLog = new RequestLog();
        requestLog.setUrl(request.getRequestURL().toString());
        requestLog.setIp(request.getRemoteAddr());
        requestLog.setCalssMethod(joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
        requestLog.setArgs(joinPoint.getArgs());
        log.info("Request : {}",requestLog);

    }
    @After("controllerLog()")
    public void doAfter(){
    }

    @AfterReturning(returning = "result",pointcut = "controllerLog()")
    public void doAfterReturn(Object result){
        log.info("Result : {}",result);
    }

    @Data
    private class RequestLog{
        private String url;
        private String ip;
        private String calssMethod;
        private Object[] args;
    }
}
