package com.sp.mvc.dao;

import com.sp.mvc.models.Person;
import org.springframework.jdbc.core.RowMapper;
import org.unbescape.json.JsonEscapeType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {

//    RowMapper<Person> rowMapper = (ResultSet resultSet, int rowNum)->{
//        Person onePerson = new Person();
//        onePerson.setId(resultSet.getInt("id"));
//        onePerson.setName(resultSet.getString("name"));
//        onePerson.setSurName(resultSet.getString("surName"));
//        onePerson.setAge(resultSet.getInt("age"));
//        onePerson.setEmail(resultSet.getString("email"));
//        return onePerson;
//    };
    @Override
    public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Person onePerson = new Person();

            //resultSet.next();//Эта строка приводит к выдаче через 1 строку в БД
            onePerson.setId(resultSet.getInt("id"));
            onePerson.setName(resultSet.getString("name"));
            onePerson.setSurName(resultSet.getString("surName"));
            onePerson.setAge(resultSet.getInt("age"));
            onePerson.setEmail(resultSet.getString("email"));
            onePerson.setTelegramChatId(resultSet.getString("telegramChatID"));
            onePerson.setRole(resultSet.getString("role"));
        //System.out.println("PersonMapper = " + onePerson.getId()+ "="+onePerson.getName());
        return onePerson;
    }
}
