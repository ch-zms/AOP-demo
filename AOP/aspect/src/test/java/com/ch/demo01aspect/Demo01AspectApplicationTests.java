package com.ch.demo01aspect;

import com.ch.demo01aspect.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Demo01AspectApplicationTests {
    @Autowired
    private UserService userService;
    @Test
    void contextLoads() {
        userService.addUser();
        userService.findUser();
    }
}
