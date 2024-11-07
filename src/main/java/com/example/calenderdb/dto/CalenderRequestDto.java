package com.example.calenderdb.dto;

import lombok.Getter;

// 캘린더 요청 DOT
@Getter
public class CalenderRequestDto {

    private String author;

    private String contents;

    private String password;

    private String createDate;

    private String changeDate;
}
