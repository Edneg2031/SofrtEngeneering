package com.example.back.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.back.dao.UserMapper;
import com.example.back.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User register(User user) {
        if (userMapper.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }
        user.setRole("user");
        user.setCreateTime(LocalDateTime.now());
        user.setBalance(0.0);
        userMapper.insertUser(user);
        return user;
    }
    @Override
    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }
    @Override
    public void changePassword(String username, String originPassword, String newPassword) {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        if (!user.getPassword().equals(originPassword)) {
            throw new RuntimeException("Invalid original password");
        }
        userMapper.updatePassword(user.getUserId(), newPassword);
    }

    @Transactional
    @Override
    public void changeUser(User user) {
        User findUser = userMapper.findById(user.getUserId());
        if (findUser == null) {
            throw new RuntimeException("User not found");
        }
        userMapper.updateUser(user);
    }

    @Override
    public void deleteUserById(long id){
        User user = userMapper.findById(id);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        userMapper.deleteUserById(id);
    }
}
