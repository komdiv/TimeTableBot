package com.sp.mvc;

import com.sp.mvc.config.SpringConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class Main {
    final BotStarter bot;

    @Autowired
    public Main(BotStarter bot) {
        this.bot = bot;
        System.out.println("@Autowired public Main(BotStarter bot)=>" + bot.getBotUsername());
        try {
            startMain();
        } catch (TelegramApiException e) {
            System.out.println("Ошибка в Main");
            throw new RuntimeException(e);
        }
    }

    public void startMain() throws TelegramApiException {
        System.out.println("Запуск телеграм бота ");
        //System.out.println("Запуск телеграм бота " + botStarter.getBotUsername());
        //BotStarter bot = new BotStarter();
        //BotStarter bot = context.getBean(BotStarter.class);
        System.out.println("bot = " + bot.getBotUsername());
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
        System.out.println("Бот 2 - запущен");
    }

//    public static void main(String[] args) throws TelegramApiException {
//        //SpringConfig springConfig = new SpringConfig();
//        System.out.println("1");
//        //AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.sp.mvc");
//        System.out.println("2");
//        //BotStarter botStarter = context.getBean("botStarter", BotStarter.class);
//        System.out.println("3");
//
//        System.out.println("Запуск телеграм бота ");
//        //System.out.println("Запуск телеграм бота " + botStarter.getBotUsername());
//        BotStarter bot = new BotStarter();
//        //BotStarter bot = context.getBean(BotStarter.class);
//        System.out.println("bot = " + bot.getBotUsername());
//        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//        telegramBotsApi.registerBot(bot);
//        System.out.println("Бот 2 - запущен");
//    }
}
