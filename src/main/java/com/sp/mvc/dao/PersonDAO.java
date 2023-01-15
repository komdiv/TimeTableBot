package com.sp.mvc.dao;

import com.sp.mvc.BotStarter;
import com.sp.mvc.config.SpringConfig;
import com.sp.mvc.models.Message;
import com.sp.mvc.models.Person;
import com.sp.mvc.models.TelegramBot;
import org.postgresql.core.SqlCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class PersonDAO {

    private TelegramBot telegramBot;

    private final JdbcTemplate jdbcTemplate;

//    @Autowired
//    public PersonDAO(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

    @Autowired
    public PersonDAO(TelegramBot telegramBot, JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.telegramBot = telegramBot;
    }

    private static int PEOPLE_COUNT;

    public List<Person> index() {
        System.out.println("DAO index");
        List<Person> list= jdbcTemplate.query("SELECT * FROM person ORDER BY id", new PersonMapper());
        //System.out.println(list.get(0).getId() +" = "+ list.get(0).getName() );
        return jdbcTemplate.query("SELECT * FROM PERSON ORDER BY id", new PersonMapper());
    }

    public Person show(int id){
        RowMapper<Person> rowMapper = (ResultSet resultSet, int rowNum)->{
            Person onePerson = new Person();
            onePerson.setId(resultSet.getInt("id"));
            onePerson.setName(resultSet.getString("name"));
            onePerson.setSurName(resultSet.getString("surName"));
            onePerson.setAge(resultSet.getInt("age"));
            onePerson.setEmail(resultSet.getString("email"));
            onePerson.setTelegramChatId(resultSet.getString("telegramChatId"));
            return onePerson;
        };
        System.out.println("мы в showDAO id=>" + id);

        List<Person> personList = jdbcTemplate.query("SELECT * FROM PERSON WHERE ID = ?", new Integer[]{id} ,rowMapper);
        //System.out.println("мы в show personList.size=>" + personList.size());
        System.out.println("person=>" + personList.stream().findAny().orElse(null));
        return personList.stream().findAny().orElse(null);
    }


//    public Person show(int id){
//        List<Person> personList = jdbcTemplate.query("SELECT * FROM PERSON WHERE ID = ?", new PersonMapper(), id);
//        return personList.stream().findAny().orElse(null);
//    }

    public void create(Person person){
        //!!! Юзеры создаются автоматически при запуске бота в телеграм !!!!!!!!!!

        System.out.println("мы в DAO. метод = create");
        List<Person> personList = jdbcTemplate.query ("SELECT * FROM PERSON WHERE ID  = (SELECT MAX(ID) FROM PERSON)", new PersonMapper());
        int idNew = personList.get(0).getId()+1;
        person.setId(idNew);
        person.setRole("user");

        System.out.println("new id = " + person.getId());
        System.out.println("new role = " + person.getRole());
//        JdbcTemplate jdbcTemplate2 = new JdbcTemplate();
        jdbcTemplate.update("INSERT INTO PERSON VALUES(?,?,?,?,?,?,?)", person.getId(), person.getName(), person.getSurName(),person.getAge(), person.getEmail(), person.getTelegramChatId(), person.getRole());
        ////////////////////////

        personList = jdbcTemplate.query ("SELECT * FROM PERSON WHERE ID  = (SELECT MAX(ID) FROM PERSON)", new PersonMapper());
        //Это для отчета в телеге
        Person newPerson = personList.get(0);

        //newPerson.setTelegramBot(new TelegramBot());
        SendMessage sMessage= new SendMessage();
        sMessage.setChatId(newPerson.getTelegramChatId());
        sMessage.setText("Создан новый человек с TelegramChatId = " + sMessage.getChatId());
        //отчет об отправке. Не работает на вымышленных телеграмID
        telegramBot.sendMessage(sMessage); //работает при верном заполнении telegramChatID

        //telegramBot.getBotStarter().sendMessage(sMessage);
        //newPerson.getTelegramBot().sendMessage(sMessage);


        //jdbcTemplate2.update("INSERT INTO PERSON VALUES (?,?,?,?,?)", person.getId(), person.getName(), person.getSurName(),person.getAge(), person.getEmail());

//        //person.setId(++PEOPLE_COUNT);
//        PreparedStatement preparedStatement = null;
//        //Statement statement = null;
//        try {
//            preparedStatement = connection.prepareStatement("SELECT MAX(ID) as MAX FROM PERSON");
//            ResultSet resultSet = preparedStatement.executeQuery();
//            resultSet.next();
//            int idNew = resultSet.getInt("MAX")+1;
//
//
//            preparedStatement = connection.prepareStatement("INSERT INTO person VALUES (?,?,?,?,?)");
//            int param=0;
//
//            preparedStatement.setInt(++param, idNew);
//            preparedStatement.setString(++param, person.getName());
//            preparedStatement.setString(++param, person.getSurName());
//            preparedStatement.setInt(++param, person.getId());
//            preparedStatement.setString(++param, person.getEmail());
//
//            preparedStatement.executeUpdate();
//
////            String SQL = String.format("INSERT INTO person values (%o,'%s','%s',%o,'%s')", person.getId(),person.getName(),person.getSurName(),person.getAge(),person.getEmail());
////            System.out.println(SQL);
////            statement.execute(SQL);
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
////        people.add(person);
    }

    public void update(int id, Person personNew){
        System.out.println("мы в ДАО = UPDATE");
        jdbcTemplate.update("UPDATE PERSON SET ID=?, NAME=?, SURNAME=?, AGE=?, EMAIL=?, telegramchatid=? WHERE ID = ?", personNew.getId(), personNew.getName(), personNew.getSurName(), personNew.getAge(), personNew.getEmail(), personNew.getTelegramChatId(), id);
//        Person onePerson = people.stream().filter(person -> person.getId()==id).findAny().orElse(null);
//        onePerson.setName(personNew.getName());
//        onePerson.setSurName(personNew.getSurName());
//        onePerson.setEmail(personNew.getEmail());

//        try {
//            //SQLData sql = "UPD"
//            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE person SET ID = ?, NAME = ?, SURNAME = ?, AGE = ?, EMAIL = ? WHERE ID = ?");
//            int param = 0;
//            preparedStatement.setInt(++param, id);
//            preparedStatement.setString(++param, personNew.getName());
//            preparedStatement.setString(++param, personNew.getSurName());
//            preparedStatement.setInt(++param, personNew.getAge());
//            preparedStatement.setString(++param, personNew.getEmail());
//            preparedStatement.setInt(++param, id);
//            preparedStatement.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    public void deleteId(int id){
        System.out.println("мы в дао делете");
        jdbcTemplate.update("DELETE FROM PERSON WHERE ID=?", id);

//        people.removeIf(person -> person.getId()==id);
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PERSON WHERE ID = ?");
//            preparedStatement.setInt(1, id);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

    }

    public boolean findByTelegramChatID(String telegramChatID){
        List<Person> personList = jdbcTemplate.query("SELECT * FROM person WHERE telegramChatID = ?",new String[]{telegramChatID}, new PersonMapper());
        Person isPerson = personList.stream().findAny().orElse(null);
        return isPerson!=null;
    }

}