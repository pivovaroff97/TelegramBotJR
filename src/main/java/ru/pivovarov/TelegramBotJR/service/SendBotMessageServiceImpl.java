package ru.pivovarov.TelegramBotJR.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.pivovarov.TelegramBotJR.TelegramBotJR;

@Service
public class SendBotMessageServiceImpl implements SendBotMessageService {

    private final TelegramBotJR telegramBotJR;

    @Autowired
    public SendBotMessageServiceImpl(TelegramBotJR telegramBotJR) {
        this.telegramBotJR = telegramBotJR;
    }
    @Override
    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);

        try {
            telegramBotJR.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
