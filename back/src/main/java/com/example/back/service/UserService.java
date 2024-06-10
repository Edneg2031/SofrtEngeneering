package com.example.back.service;


import com.example.back.entity.User;

public interface UserService {
    User register(User user);
    User login(String username, String password);  // 新增登录方法声明
    void changePassword(String username, String originPassword, String newPassword);
    void changeUser(User user);
    void deleteUserById(long id);
}

