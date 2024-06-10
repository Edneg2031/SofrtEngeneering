package com.example.back.entity;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String username;
    private String originPassword;
    private String newPassword;

    // Getters and Setters
}
