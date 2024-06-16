package com.example.back.controller;


import com.example.back.entity.ChangePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.back.entity.User;
import com.example.back.service.UserService;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;


    // 进行注册
    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody User user) {
        System.out.println(user);
        try {
            User registeredUser = userService.register(user);
            System.out.println(registeredUser);
            return ResponseEntity.ok(registeredUser.getUserId());
        } catch (RuntimeException e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    // 进行登录
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User loginRequest) {
        try {
            User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
            System.out.println(user);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    // 修改用户密码
    @PostMapping("/change")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {

        try {
            userService.changePassword(changePasswordRequest.getUsername(), changePasswordRequest.getOriginPassword(), changePasswordRequest.getNewPassword());
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 修改用户信息
    @PostMapping("/updateUserInfo")
    public ResponseEntity<Void> changeUserInfo(@RequestBody User user){
        System.out.println(user);
        try{

            userService.changeUser(user);
            return ResponseEntity.ok().build();
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().build();
        }
    }
    // 删除用户
    @DeleteMapping("/deleteUserInfo/{id}")
    public ResponseEntity<Void> deleteUserInfo(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

