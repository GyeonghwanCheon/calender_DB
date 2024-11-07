package com.example.calenderdb.repository;

import com.example.calenderdb.dto.CalenderResponseDto;
import com.example.calenderdb.entity.Calender;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CalenderRepository {

    CalenderResponseDto saveCalender(Calender calender);

//    List<CalenderResponseDto> findAllCalenders();
//
//    Calender findCalenderById(Long id);
//
//    void deleteCalender (Long id);
}
