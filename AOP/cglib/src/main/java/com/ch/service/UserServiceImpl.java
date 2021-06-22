package com.ch.service;

import org.springframework.stereotype.Service;

/**
 * @author ch
 */
@Service
public class UserServiceImpl implements UserService{

    @Override
    public void addUser() {
        System.out.println("添加");
    }

    @Override
    public void findUser() {
        System.out.println("查询");
    }
}
