package ru.pivovarov.TelegramBotJR.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.pivovarov.TelegramBotJR.service.SendBotMessageService;

import static ru.pivovarov.TelegramBotJR.command.CommandName.STAT;

public class AdminHelpCommand implements Command {
    public static final String ADMIN_HELP_MESSAGE = String.format("""
                    ✨<b>Доступные команды админа</b>✨

                    <b>Получить статистику</b>
                    %s - статистика бота
                    """,
            STAT.getCommandName());

    private final SendBotMessageService sendBotMessageService;

    public AdminHelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId(), ADMIN_HELP_MESSAGE);
    }
}
