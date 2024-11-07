package com.example.calenderdb.repository;

import com.example.calenderdb.dto.CalenderResponseDto;
import com.example.calenderdb.entity.Calender;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class JdbcTemplateCalenderRepository implements CalenderRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateCalenderRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public CalenderResponseDto saveCalender(Calender calender) {
        //INSERT Query를 직접 작성하지 않아도 됌.
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("calenderDB").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("author", calender.getAuthor());
        parameters.put("contenst", calender.getContents());
        parameters.put("password", calender.getPassword());
        parameters.put("createDate", calender.getCreateDate());
        parameters.put("changeDate", calender.getChangeDate());

        // 저장 후 생성된 key 값을 Number 타입으로 반환
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new CalenderResponseDto(key.longValue(), calender.getAuthor(), calender.getContents(), calender.getPassword(),
                calender.getCreateDate(),calender.getChangeDate());
    }

//    @Override
//    public List<CalenderResponseDto> findAllCalenders() {
//        return null;
//    }
//
//    @Override
//    public Calender findCalenderById(Long id) {
//        return null;
//    }
//
//    @Override
//    public void deleteCalender(Long id) {
//
//    }
}

