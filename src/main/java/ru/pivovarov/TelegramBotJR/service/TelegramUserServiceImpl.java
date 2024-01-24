package ru.pivovarov.TelegramBotJR.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pivovarov.TelegramBotJR.repository.TelegramUserRepository;
import ru.pivovarov.TelegramBotJR.repository.entity.TelegramUser;

import java.util.List;
import java.util.Optional;

@Service
public class TelegramUserServiceImpl implements TelegramUserService {

    @Autowired
    private TelegramUserRepository telegramUserRepository;
    @Override
    public void save(TelegramUser user) {
        telegramUserRepository.save(user);
    }

    @Override
    public Optional<TelegramUser> findByChatId(Long chatId) {
        return telegramUserRepository.findById(chatId);
    }

    @Override
    public List<TelegramUser> retrieveAllActiveUsers() {
        return telegramUserRepository.findAllByActiveTrue();
    }

    @Override
    public List<TelegramUser> findAllInActiveUsers() {
        return telegramUserRepository.findAllByActiveFalse();
    }
}
