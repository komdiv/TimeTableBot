package com.sp.mvc.controllers;

import com.sp.mvc.dao.MessageDAO;
import com.sp.mvc.dao.PersonDAO;
import com.sp.mvc.models.Message;
import com.sp.mvc.models.Person;
//import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    private final MessageDAO messageDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO, MessageDAO messageDAO) {
        System.out.println("Контроллер PeopleController is ready");
        this.personDAO = personDAO;
        this.messageDAO = messageDAO;
    }

    @GetMapping()
    public String index(Model model){
        System.out.println("мы в контроллере INDEX");
        //Список всех людей
        model.addAttribute("people", personDAO.index());
        //model.addAttribute("chats", messageDAO.getAllChats());
        System.out.println(personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        //Получим одного человека по id из DAO и передадим на отображение
        model.addAttribute("person", personDAO.show(id));
        System.out.println("шаг 1");
        model.addAttribute("chats", messageDAO.getAllChats());
        System.out.println("шаг 2");
        model.addAttribute("message", messageDAO.getNewEmptyMessage());
        System.out.println("шаг 3");
//        System.out.println("chats = " + messageDAO.getAllChats().get(0).getText());
        return "people/show";
    }

    @GetMapping("/new")
    public String newPersonForm(Model model){
        model.addAttribute("person", new Person());
        System.out.println("мы в контроллере newPersonForm");
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        System.out.println("мы в контроллере create. Имя = create");
        if (bindingResult.hasErrors()) return "people/new";

        //тут добавим через БД
        personDAO.create(person);// почему не эта строка? Да вроде эта.
        System.out.println("2  мы в контроллере. Имя = create");
//        return "/people/successPage";
        return "redirect:/people";
    }

    @PostMapping("{id}")
    public String sendMessageToCommonChat(@PathVariable("id") int id, @ModelAttribute("message") Message message, BindingResult bindingResult){
        System.out.println("мы в контроллере send Message");
        System.out.println("Текст сообщения = " + message.getText());
        if(bindingResult.hasErrors()) return "people/show";
        Message newMessage = new Message();
        newMessage.setTime(LocalDateTime.now());
        newMessage.setPersonId(id);
        newMessage.setText(message.getText());
        newMessage.setPersonName(personDAO.show(id).getName());

        messageDAO.addMessage(newMessage); // to BD
        System.out.println("занесли в БД = " + newMessage.getText() +" from " +newMessage.getPersonName());


        //return "redirect:people/";
        return "redirect:/people/"+id;
    }

    @GetMapping("{id}/edit")
    public String editPersonForm(@PathVariable("id") int id, Model model){
        //отсюда уходим со ссылкой на editForm
        System.out.println("Мы в контроллере {id}/edit");
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
//        return "people/show";
    }

    @PatchMapping("{id}")
    public String patchPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id){
        System.out.println("мы в контроллере PATCH Mapping");
        if(bindingResult.hasErrors()) return "people/edit";

        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        System.out.println("мы в контроллере delete");
        personDAO.deleteId(id);
        return "redirect:/people";
    }




//    @PostMapping("====")
//    public String addPerson(@RequestParam("name") String name, @RequestParam("surName") String surName,
//            @RequestParam("iMail") String iMail, Model model){
//        //add new Person
//        System.out.println("мы в контроллере. Имя =");
//        Person person = new Person();
//
//        person.setName(name);
//        person.setSurName(surName);
//        person.setEmail(iMail);
//
//        //тут добавим через БД
//        personDAO.create(person);// почему не эта строка? Да вроде эта.
//
//        model.addAttribute("person", person);
//
//        return "/people/successPage";
//    }

    @PostMapping("successPage")
    public String successPage(){
//    public String addPerson2(@RequestParam("name") String name, Model model){

            System.out.println("successPage");
        return "people/successPage";
    }
}
