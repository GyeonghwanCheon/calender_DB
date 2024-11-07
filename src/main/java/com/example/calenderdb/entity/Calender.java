package com.example.calenderdb.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;


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

}
