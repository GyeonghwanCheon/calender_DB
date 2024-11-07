package com.example.calenderdb.repository;

import com.example.calenderdb.dto.CalenderResponseDto;
import com.example.calenderdb.entity.Calender;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CalenderRepository {

    CalenderResponseDto saveCalender(Calender calender);

    List<CalenderResponseDto> findAllCalenders();

    Calender findCalenderByIdOrElseThrow(Long id);

    int updateCalender(Long id, String author, String contents);
//
    int deleteCalender (Long id);
}
