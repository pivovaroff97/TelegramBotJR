package ru.pivovarov.TelegramBotJR.service;

public interface SendBotMessageService {

    void sendMessage(String chatId, String message);
}
