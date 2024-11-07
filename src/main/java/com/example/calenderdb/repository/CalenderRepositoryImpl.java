package com.example.calenderdb.repository;

import com.example.calenderdb.dto.CalenderResponseDto;
import com.example.calenderdb.entity.Calender;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CalenderRepositoryImpl implements CalenderRepository {

    private final Map<Long, Calender> calenderList = new HashMap<>();


    @Override
    public Calender saveCalender(Calender calender) {

        // memo 식별자 자동 생성
        Long calenderId = calenderList.isEmpty() ? 1 : Collections.max(calenderList.keySet()) + 1;
        calender.setId(calenderId);

        calenderList.put(calenderId, calender);

        return calender;
    }

    @Override
    public List<CalenderResponseDto> findAllCalenders() {

        //init List
        List<CalenderResponseDto> allCalenders = new ArrayList<>();


        //HashMap<Calender> -> List<CalenderResponseDto>
        for(Calender calender : calenderList.values()) {
            CalenderResponseDto responseDto = new CalenderResponseDto(calender);
            allCalenders.add(responseDto);
        }

        // 수정일 날짜 기준 내림차순
        allCalenders.sort(Comparator.comparing(CalenderResponseDto::getChangeDate).reversed());

        return allCalenders;
    }

    @Override
    public Calender findCalenderById(Long id) {
        return calenderList.get(id);
    }

    @Override
    public void deleteCalender(Long id) {
        calenderList.remove(id);
    }
}
