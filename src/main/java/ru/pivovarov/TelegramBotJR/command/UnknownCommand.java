package ru.pivovarov.TelegramBotJR.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.pivovarov.TelegramBotJR.service.SendBotMessageService;

public class UnknownCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public final static String UNKNOWN_MESSAGE = "Не понимаю вас \\uD83D\\uDE1F, напишите /help чтобы узнать что я понимаю.";

    // Здесь не добавляем сервис через получение из Application Context.
    // Потому что если это сделать так, то будет циклическая зависимость, которая
    // ломает работу приложения.
    public UnknownCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), UNKNOWN_MESSAGE);
    }
}
