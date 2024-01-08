package ru.pivovarov.TelegramBotJR.service;

import ru.pivovarov.TelegramBotJR.repository.entity.TelegramUser;

import java.util.List;
import java.util.Optional;

public interface TelegramUserService {
    void save(TelegramUser user);
    Optional<TelegramUser> findByChatId(String chatId);
    List<TelegramUser> retrieveAllActiveUsers();
}
