package com.example.calenderdb.service;

import com.example.calenderdb.dto.CalenderRequestDto;
import com.example.calenderdb.dto.CalenderResponseDto;

import javax.swing.*;
import java.util.List;


public interface CalenderService {
    CalenderResponseDto saveCalender(CalenderRequestDto Dto);

    List<CalenderResponseDto> findAllCalenders();

    CalenderResponseDto findCalenderById(Long id);

    CalenderResponseDto updateCalender(Long id, String author, String contents, String password);

    void deleteCalender (Long id, String password);
//    void deleteCalender (Long id, String password);
}
