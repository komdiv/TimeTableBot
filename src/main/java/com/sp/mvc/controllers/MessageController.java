package com.sp.mvc.controllers;

import com.sp.mvc.dao.MessageDAO;
import com.sp.mvc.dao.PersonDAO;
import com.sp.mvc.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/message")
public class MessageController {

    private final MessageDAO messageDAO;

    private  final PersonDAO personDAO;

    @Autowired
    public MessageController(MessageDAO messageDAO, PersonDAO personDAO) {
        this.messageDAO = messageDAO;
        this.personDAO = personDAO;
    }

    @PostMapping()
    public String sendMessageToCommonChat(@ModelAttribute("message") Message message, BindingResult bindingResult){
        System.out.println("мы в контроллере MessageController => send Message");
        System.out.println(" message.getPersonId()=>" + message.getPersonId());
        System.out.println("Текст сообщения = " + message.getText());
        if(bindingResult.hasErrors()) return "people/show";
        Message newMessage = new Message();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd  HH:mm");
        System.out.println("LocalDate.now()=>" + LocalDateTime.now().format(formatter));
        //newMessage.setTimeDay(LocalDateTime.now());
        //newMessage.setTimeDay(LocalDate.now());
        newMessage.setTime(LocalDateTime.now());
        //newMessage.setPersonId(id);//позднее вычисляется. надо сюда перенести?
        newMessage.setPersonId(message.getPersonId());//позднее вычисляется. надо сюда перенести?
        newMessage.setText(message.getText());
        newMessage.setPersonName(personDAO.show(message.getPersonId()).getName());
        System.out.println(" newMessage.getPersonName()=>" + newMessage.getPersonName());
        messageDAO.addMessage(newMessage); // to BD
        System.out.println("занесли в БД = " + newMessage.getText() +" from " +newMessage.getPersonName());


        //return "redirect:people/";
        return "redirect:/people/"+message.getPersonId();
    }

}
