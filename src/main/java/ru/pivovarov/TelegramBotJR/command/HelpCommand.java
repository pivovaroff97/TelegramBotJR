package ru.pivovarov.TelegramBotJR.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.pivovarov.TelegramBotJR.service.SendBotMessageService;

import static ru.pivovarov.TelegramBotJR.command.CommandName.*;

public class HelpCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public static final String HELP_MESSAGE = String.format("""
                    ✨<b>Дотупные команды</b>✨

                    <b>Начать\\закончить работу с ботом</b>
                    %s - начать работу со мной
                    %s - приостановить работу со мной
                    
                    Работа с подписками на группы:
                    %s - подписаться на группу статей
                    %s - получить список групп, на которые подписан
                    %s - отписаться от группы статей

                    %s - получить помощь в работе со мной
                    %s - узнать количество активных пользователей
                    """,
            START.getCommandName(), STOP.getCommandName(),
            ADD_GROUP_SUB.getCommandName(), LIST_GROUP_SUB.getCommandName(),
            DELETE_GROUP_SUB.getCommandName(),
            HELP.getCommandName(), STAT.getCommandName());

    // Здесь не добавляем сервис через получение из Application Context.
    // Потому что если это сделать так, то будет циклическая зависимость, которая
    // ломает работу приложения.
    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }
    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), HELP_MESSAGE);
    }
}
