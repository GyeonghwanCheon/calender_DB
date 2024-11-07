package com.example.calenderdb.service;

import com.example.calenderdb.dto.CalenderRequestDto;
import com.example.calenderdb.dto.CalenderResponseDto;
import com.example.calenderdb.entity.Calender;
import com.example.calenderdb.repository.CalenderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CalenderServiceImpl implements CalenderService {

    private final CalenderRepository calenderRepository;

    public CalenderServiceImpl(CalenderRepository calenderRepository) {
        this.calenderRepository = calenderRepository;
    }


    @Override
    public CalenderResponseDto saveCalender(CalenderRequestDto dto) {

        LocalDateTime dateTime = LocalDateTime.now();
        // 원하는 포맷 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 포맷 적용
        String formattedDate = dateTime.format(formatter);

        // 요청받은 데이터로 Calender 객체 생성 ID 없음
        Calender calender = new Calender(dto.getAuthor(), dto.getContents(),
                dto.getPassword(), formattedDate, formattedDate);


        return calenderRepository.saveCalender(calender);
    }

    @Override
    public List<CalenderResponseDto> findAllCalenders() {
        return calenderRepository.findAllCalenders();
    }


    @Override
    public CalenderResponseDto findCalenderById(Long id) {

        Calender calender = calenderRepository.findCalenderByIdOrElseThrow(id);

        return new CalenderResponseDto(calender);
    }

    @Transactional
    @Override
    public CalenderResponseDto updateCalender(Long id, String author, String contents, String password) {
        //필수값 검증
        if(author == null || contents == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        int updatedRow = calenderRepository.updateCalender(id, author, contents);

        if(updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data has been modified");
        }

        Calender calender = calenderRepository.findCalenderByIdOrElseThrow(id);

        if(!Objects.equals(calender.getPassword(),password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The passwords are not the same.");
        }


        return new CalenderResponseDto(calender);
    }



    @Override
    public void deleteCalender(Long id, String password) {

        Calender calender = calenderRepository.findCalenderByIdOrElseThrow(id);

        if(!Objects.equals(calender.getPassword(),password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The passwords are not the same.");
        }

        // calender 삭제
        int deletedRow = calenderRepository.deleteCalender(id);

        if(deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }




    }


//    @Override
//    public CalenderResponseDto findCalenderById(Long id) {
//
//        Calender calender = calenderRepository.findCalenderById(id);
//
//        if(calender == null){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
//        }
//
//        return new CalenderResponseDto(calender);
//    }
//
//    @Override
//    public CalenderResponseDto updateCalender(Long id, String author, String contents, String password) {
//
//        Calender calender = calenderRepository.findCalenderById(id);
//
//        //NPE 방지
//        if(calender == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
//        }
//
//        // 작성자와 할 일 수정
//        if(author == null || contents == null) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The author and contents are required values.");
//        }
//
//        //비밀번호가 같은 지 확인
//        if(!Objects.equals(calender.getPassword(),password)) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The passwords are not the same.");
//        }
//
//        calender.update(author, contents, password);
//
//        return new CalenderResponseDto(calender);
//    }
//
//    @Override
//    public void deleteCalender(Long id, String password) {
//        Calender calender = calenderRepository.findCalenderById(id);
//
//        //calender의 key값을 id를 포함하고 있다면 그리고 요청받은 패스워드 값이 같다면.
//        if(calender != null) {
//
//            if (Objects.equals(calender.getPassword(), password)) {
//
//                calenderRepository.deleteCalender(id);
//            } else {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The passwords are not the same.");
//            }
//
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
//        }
//    }


}
