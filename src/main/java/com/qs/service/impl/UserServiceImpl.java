package com.qs.service.impl;

import com.qs.entity.User;
import com.qs.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public User getUserByUserId(String userId) {
        User user = new User();
        user.setUserId("1111");
        user.setUserName("张三");
        return user;
    }
}
