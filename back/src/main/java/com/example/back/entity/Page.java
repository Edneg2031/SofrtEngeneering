package com.example.back.entity;


import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    private int current;
    private int size;
    private List<T> records;
    private long total;
    public Page(int page, int size){
        this.current = page;
        this.size = size;
    }
}
