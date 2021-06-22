package com.ch;

import com.ch.service.UserService;
import com.ch.service.UserServiceImpl;

import java.lang.reflect.Proxy;

/**
 * JDK动态代理
 * @author ch
 */
public class JdkProxy {
    public static void main(String[] args) {
        //1. 创建出被代理的对象
        UserService userService = new UserServiceImpl();
        //类加载器
        ClassLoader classLoader = userService.getClass().getClassLoader();
        //使用jdk的动态代理，增强UserServiceImpl对象的方法:在增删改之前做权限校验
        //参数1:类加载器   参数2:要代理的接口
        UserService proxy = (UserService) Proxy.newProxyInstance(classLoader, new Class[]{UserService.class},
                (proxy1, method, args1) -> {
                    //invoke方法会在代理对象调用任意方法的时候执行,所以我们就在invoke方法中编写代理规则
                    //1. 判断调用的方法是否是增删改
                    String methodName = method.getName();
                    if ("addUser".equals(methodName)) {
                        //那么就先进行权限校验
                        System.out.println("模拟进行权限校验...");
                        //再执行原本的增删改方法
                        return method.invoke(userService, args1);
                    }
                    //不是添加方法，那么就执行原本的方法
                    return method.invoke(userService, args1);
                });
        proxy.findUser();
        proxy.addUser();
    }
}
