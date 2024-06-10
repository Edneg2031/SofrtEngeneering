package com.example.back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class User {
    private Long userId;
    private String username;
    private String password;
    private String email;
    private String role;

    private LocalDateTime createTime;
    private Double balance;  // 新增余额属性
}
