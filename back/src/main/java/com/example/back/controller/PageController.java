package com.example.back.controller;


import com.example.back.dao.UserMapper;
import com.example.back.entity.Page;
import com.example.back.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class PageController {

    @Autowired
    UserMapper userMapper;
    @GetMapping("/getUserInfoListByPage")
    public Page<User> getUserInfoListByPage(
            @RequestParam(name="page") int page,
            @RequestParam(name="size") int size,
            @RequestParam(name="name") String name){

        Page<User> userInfoPage = new Page<>(page, size);
        int start = (page - 1) * size; // 计算起始位置

        int total = 0;
        List<User> userInfos = null;
        if(name.equals("")) {
            total = userMapper.getUserCount();
            userInfos = userMapper.getUserListByPage(start, size);
        }else{
            total  = userMapper.countUserListByName(name);

            userInfos = userMapper.getUserListByName(start, size, name);
        }
        userInfoPage.setRecords(userInfos);
        userInfoPage.setTotal(total);
        return userInfoPage;
    }

}
