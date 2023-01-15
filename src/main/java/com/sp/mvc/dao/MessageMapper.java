package com.sp.mvc.dao;

import com.sp.mvc.models.Message;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static java.time.LocalTime.now;

public class MessageMapper implements RowMapper<Message> {

    @Override
    public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
        Message oneMessage = new Message();

        // SELECT to_char(createddate, 'yyyymmdd hh:mi:ss tt') as created_date FROM table
        oneMessage.setId(rs.getInt("chatId"));
        //oneMessage.setTime(rs.getDate("time").toLocalDate().atTime(now()));
        oneMessage.setTime(rs.getTimestamp("time").toLocalDateTime());
//        oneMessage.setTime(rs.getTime("time").toLocalTime());
//        oneMessage.setTime(rs.getTime("time").toLocalTime());
        oneMessage.setPersonId(rs.getInt("personId"));
        oneMessage.setText(rs.getString("message"));
        oneMessage.setPersonName(rs.getString("personName"));
        return oneMessage;
    }
}
