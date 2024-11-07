package com.example.calenderdb.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@AllArgsConstructor
@Getter
public class Calender {

    private Long id; // 식별자 ID
    private String author; // 작성자
    private String contents; // 할 일
    private String password; // 비밀번호
    private String createDate; // 작성일
    private String changeDate; // 수정일


    public Calender(String author, String contents, String password, String createDate, String changeDate) {
        this.author = author;
        this.contents = contents;
        this.password = password;
        this.createDate = createDate;
        this.changeDate = changeDate;
    }


    public void update(String author, String contents, String password) {
        this.author = author;
        this.contents = contents;
        this.password = password;


        LocalDateTime dateTime = LocalDateTime.now();
        // 원하는 포맷 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 포맷 적용
        String formattedDate = dateTime.format(formatter);
        this.changeDate = formattedDate;
    }
}
