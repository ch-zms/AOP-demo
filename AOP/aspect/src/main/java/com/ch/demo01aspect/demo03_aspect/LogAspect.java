package com.ch.demo01aspect.demo03_aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author ch
 */
@Aspect
@Component
public class LogAspect {
    /**
     * 定义切点
     */
    private final String POINT_CUT = "execution(* com.ch.demo01aspect.service.UserServiceImpl.*(..))";

        @Pointcut(value = POINT_CUT)
        public void pointCut() {
        }

        /**
         * 前置增强方法
         *
         */
        @Before(value = "pointCut()")
        public void before() {
            System.out.println("模拟进行权限校验...");
        }

        /**
         * 后置增强，方法正常退出时执行
         */
        @AfterReturning(value = "pointCut()")
        public void afterReturning() {
            System.out.println("后置增加，方法正常退出时执行");
        }

        /**
         * 异常抛出增强，方法执行抛出异常后执行。
         * throwing:限定了只有目标方法抛出的异常与通知方法相应参数异常类型时才能执行后置异常通知，否则不执行，
         * 对于throwing对应的通知方法参数为 Throwable 类型将匹配任何异常。
         *
         * @param exception error
         */
        @AfterThrowing(value = "pointCut()", throwing = "exception")
        public void afterThrowing(Throwable exception) {
            System.out.println("错误异常");
        }

        /**
         * final增强，不管是抛出异常或者正常退出都会执行。
         */
        @After(value = "pointCut()")
        public void after() {
            System.out.println("final增强");
        }

        /**
         * 环绕通知：
         * 注意:Spring AOP的环绕通知会影响到AfterThrowing通知的运行,不要同时使用
         * 环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值。
         * 环绕通知第一个参数必须是org.aspectj.lang.ProceedingJoinPoint类型
         */
        @Around(value = "pointCut()")
        public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
            MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
            // 获取当前方法对象
            Method method = methodSignature.getMethod();
            String methodName = method.getName();
            if ("addUser".equals(methodName)) {
                System.out.println("模拟进行权限校验...");
                //再执行原本的增删改方法
                return proceedingJoinPoint.proceed();
            }
            return proceedingJoinPoint.proceed();
        }
}
