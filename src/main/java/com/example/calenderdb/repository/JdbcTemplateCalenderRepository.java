package com.example.calenderdb.repository;

import com.example.calenderdb.dto.CalenderResponseDto;
import com.example.calenderdb.entity.Calender;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Repository
public class JdbcTemplateCalenderRepository implements CalenderRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateCalenderRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 일정 생성
    @Override
    public CalenderResponseDto saveCalender(Calender calender) {
        //INSERT Query를 직접 작성하지 않아도 됌.
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("calenderDB").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("author", calender.getAuthor());
        parameters.put("contents", calender.getContents());
        parameters.put("password", calender.getPassword());
        parameters.put("createDate", calender.getCreateDate());
        parameters.put("changeDate", calender.getChangeDate());

        // 저장 후 생성된 key 값을 Number 타입으로 반환
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new CalenderResponseDto(key.longValue(), calender.getAuthor(), calender.getContents(), calender.getPassword(),
                calender.getCreateDate(),calender.getChangeDate());
    }

    // 일정 전체 조회
    @Override
    public List<CalenderResponseDto> findAllCalenders() {
        return jdbcTemplate.query("select * from calenderdb order by changeDate desc", calenderRowMapper());
    }

    // 일정 단건 조회
    @Override
    public Calender findCalenderByIdOrElseThrow(Long id) {
        List<Calender> result = jdbcTemplate.query("select * from calenderdb where id = ?", calenderRowMapperV2(), id);

        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    // 일정 수정
    @Override
    public int updateCalender(Long id, String author, String contents) {

        LocalDateTime dateTime = LocalDateTime.now();
        // 원하는 포맷 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 포맷 적용
        String formattedDate = dateTime.format(formatter);

        return jdbcTemplate.update("update calenderdb set author = ?, contents = ?, changeDate = ? where id = ?",
                author, contents, formattedDate, id);
    }

    // 일정 삭제
    @Override
    public int deleteCalender(Long id) {
        return jdbcTemplate.update("delete from calenderdb where id = ?", id);
    }


    public RowMapper<CalenderResponseDto> calenderRowMapper() {
        return new RowMapper<CalenderResponseDto>() {
            @Override
            public CalenderResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new CalenderResponseDto(
                        rs.getLong("id"),
                        rs.getString("author"),
                        rs.getString("contents"),
                        rs.getString("password"),
                        rs.getString("createDate"),
                        rs.getString("changeDate")
                );
            }
        };
    }

    private RowMapper<Calender> calenderRowMapperV2() {
        return new RowMapper<Calender>() {
            @Override
            public Calender mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Calender(
                        rs.getLong("id"),
                        rs.getString("author"),
                        rs.getString("contents"),
                        rs.getString("password"),
                        rs.getString("createDate"),
                        rs.getString("changeDate")
                );
            }

        };
    }



}

