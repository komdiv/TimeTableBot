package com.sp.mvc.models;


import com.sp.mvc.BotStarter;
import com.sp.mvc.dao.PersonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
//@Scope("prototype")
public class TelegramBot {

    BotStarter botStarter;
    static long telegramChatId = 1220883049;
    SendMessage sMessage;

    @Autowired
    public TelegramBot(BotStarter botStarter) {
        this.botStarter = botStarter;
    }

    public void sendMessage(SendMessage sMessage){
        botStarter.sendMessage(sMessage);
    }

    public PersonDAO getPersonDAO(){
        return botStarter.getPersonDAO();
    }


    //------ set get
    public BotStarter getBotStarter() {
        return botStarter;
    }

    @Autowired
    public void setBotStarter(BotStarter botStarter) {
        System.out.println("botStarter устанавливается через setBotStarter =>" + botStarter.getBotUsername());
        this.botStarter = botStarter;
    }

    public static long getTelegramChatId() {
        return telegramChatId;
    }

    public static void setTelegramChatId(long telegramChatId) {
        TelegramBot.telegramChatId = telegramChatId;
    }

    public SendMessage getsMessage() {
        return sMessage;
    }

    public void setsMessage(SendMessage sMessage) {
        this.sMessage = sMessage;
    }
}
