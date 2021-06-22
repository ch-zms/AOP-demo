package com.ch;

import com.ch.service.UserServiceImpl;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

/**
 * Cglib方式
 * @author ch
 */
public class CglibProxy {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        //1. 创建Enhancer对象
        Enhancer enhancer = new Enhancer();
        //2. 设置父类(要代理的类)
        enhancer.setSuperclass(UserServiceImpl.class);
        //3. 设置回调，在回调中编写代理规则
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            //编写代理规则
            //1. 判断执行的方法，是否是增删改方法
            String methodName = method.getName();
            if ("addUser".equals(methodName)) {
                //先执行权限校验
                System.out.println("模拟执行权限校验");
                //调用原本的增删改方法
                return method.invoke(userService,objects);
            }
            //如果不需要增强的方法，就执行原本的方法
            return method.invoke(userService,objects);
        });

        //4. 创建代理对象
        UserServiceImpl proxy = (UserServiceImpl) enhancer.create();

        proxy.findUser();
        proxy.addUser();
    }
}
