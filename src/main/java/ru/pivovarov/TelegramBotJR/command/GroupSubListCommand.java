package ru.pivovarov.TelegramBotJR.command;

import jakarta.ws.rs.NotFoundException;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.pivovarov.TelegramBotJR.repository.entity.TelegramUser;
import ru.pivovarov.TelegramBotJR.service.SendBotMessageService;
import ru.pivovarov.TelegramBotJR.service.TelegramUserService;

import java.util.stream.Collectors;

public class GroupSubListCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public GroupSubListCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        //todo add exception handling
        TelegramUser telegramUser = telegramUserService.findByChatId(chatId)
                .orElseThrow(NotFoundException::new);

        String message = "Я нашел все подписки на группы: \n\n";
        String collectedGroups = telegramUser.getGroupSubs().stream()
                .map(it -> "Группа: " + it.getTitle() + " , ID = " + it.getId() + " \n")
                .collect(Collectors.joining());

        sendBotMessageService.sendMessage(telegramUser.getChatId(), message + collectedGroups);
    }
}
