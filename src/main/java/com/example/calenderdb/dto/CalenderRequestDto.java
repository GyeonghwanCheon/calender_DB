package com.example.calenderdb.dto;

import lombok.Getter;


@Getter
public class CalenderRequestDto {

    private String author;

    private String contents;

    private String password;

    private String createDate;

    private String changeDate;
}
