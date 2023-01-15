package com.sp.mvc;

import com.sp.mvc.dao.PersonDAO;
import com.sp.mvc.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@Component
public class BotStarter extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botUserName;

    @Value("${bot.coin}")
    private String botToken;

    private PersonDAO personDAO;

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println("пришло = " + update.getMessage().getText());
        System.out.println("Твой чат ID =>" + update.getMessage().getChatId());
        //Message message = new Message();
        SendMessage sMessage = new SendMessage();

//        try {
//            sMessage = new SendMessage();
//        }catch (Exception e){
//            System.out.println("ошибка SendMessage()");
//        }


        System.out.println("контроль 1");
        sMessage.setText("Сам ты такой как " + update.getMessage().getText());
        System.out.println("контроль 2");
        sMessage.setChatId(update.getMessage().getChatId().toString());
        //SendAnimation sendAnimation = new SendAnimation();
        //sendAnimation.setChatId(update.getChatId());
        //sendAnimation.setAnimation(new InputFile(getDialogBot().getUrlPicture()));
        //sendAnimation.setDisableNotification(true);
        //sendAnimation.setCaption("это тестовое сообщение Caption");
        System.out.println("контроль 3");
        sendMessage(sMessage);

        System.out.println("юзер известен? " + personDAO.findByTelegramChatID(update.getMessage().getChatId().toString()));
        //-------------автодобавление в бд
        if(!personDAO.findByTelegramChatID(update.getMessage().getChatId().toString())){

            Person newPerson = new Person();
            newPerson.setName(update.getMessage().getFrom().getFirstName());
            newPerson.setSurName(update.getMessage().getFrom().getLastName());
            newPerson.setAge(18);
            newPerson.setEmail("empty@gmail.com");
            newPerson.setTelegramChatId(update.getMessage().getChatId().toString());
            personDAO.create(newPerson);
        }
        //===================================

    }

    public void sendMessage(SendMessage sMessage){
        try {
            System.out.println("Отправляется =>" + sMessage.getText());
            execute(sMessage);
        } catch (TelegramApiException e) {
            System.out.println("ОШИБКА отправки");
            throw new RuntimeException(e);
        }
    }



    @PostConstruct
    public void init(){
        System.out.println("@PostConstruct this.botUserName " + this.getBotUsername());
    }

    public BotStarter() {
        super(new DefaultBotOptions());
        System.out.println("this.botUserName =>" + this.getBotUsername());
//        FileInputStream fis;
//        Properties property = new Properties();
//        try {
//             //fis = new FileInputStream("src/main/resources/Setting.properties");
//            fis = new FileInputStream("classpath:Setting.properties");
//
//             property.load(fis);
//        }catch (Exception e) {
//            System.err.println("ОШИБКА: Файл свойств отсуствует!");
//        }
//        this.botUserName = property.getProperty("bot.name");
//        this.botToken = property.getProperty("bot.coin");
//        System.out.println("Construktor: " + botUserName);
    }

    //----------------------get set----------------------------

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void setBotUserName(String botUserName) {
        this.botUserName = botUserName;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public PersonDAO getPersonDAO() { return personDAO; }

    @Autowired
    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }
}
