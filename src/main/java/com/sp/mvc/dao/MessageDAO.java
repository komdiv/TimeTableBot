package com.sp.mvc.dao;

import com.sp.mvc.models.Message;
import com.sp.mvc.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class MessageDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MessageDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Message getNewEmptyMessage(){
        return new Message();
    }

    public List<Message> getAllChats(){
        System.out.println("мы в messageDAO. getAllChats");
        List<Message> messageList = jdbcTemplate.query("SELECT * from chats order by chatid desc", new MessageMapper());
        System.out.println("мы в messageDAO. getAllChats ГОТОВО");
        return messageList;
    }

    public void addMessage(Message message){
        System.out.println("мы в messageDAO. Add Message");
        System.out.println("мы в messageDAO. message.getText()=>" + message.getText());
        //тут вычислим порядковый messageId
        List<Message> messageList = jdbcTemplate.query("SELECT * FROM chats WHERE chatid=(SELECT MAX(chatid) FROM chats)", new MessageMapper());
        Message oneMessage = messageList.stream().findAny().orElse(null);
        int newId = oneMessage.getId()+1;

        System.out.println("время = " + message.getTime());//chatid// было = chatid
        jdbcTemplate.update("INSERT INTO chats values(?, ?, ?, ?, ?)", newId, message.getPersonId(),message.getText(),message.getPersonName(), message.getTime());

    }

//    public String getNamePerson(int id){
//        Person person =
//    }


}
