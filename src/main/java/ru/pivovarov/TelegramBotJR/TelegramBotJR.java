package ru.pivovarov.TelegramBotJR;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.pivovarov.TelegramBotJR.command.CommandContainer;
import ru.pivovarov.TelegramBotJR.jrclient.JavaRushGroupClient;
import ru.pivovarov.TelegramBotJR.service.GroupSubService;
import ru.pivovarov.TelegramBotJR.service.SendBotMessageServiceImpl;
import ru.pivovarov.TelegramBotJR.service.StatisticsService;
import ru.pivovarov.TelegramBotJR.service.TelegramUserService;

import java.util.List;

import static ru.pivovarov.TelegramBotJR.command.CommandName.NO;

@Component
public class TelegramBotJR extends TelegramLongPollingBot {

    @Value("${tg-bot.username}")
    private String username;

    private final CommandContainer commandContainer;

    public static String COMMAND_PREFIX = "/";

    @Autowired
    public TelegramBotJR(@Value("${tg-bot.token}") String botToken, TelegramUserService telegramUserService,
                         JavaRushGroupClient groupClient, GroupSubService groupSubService,
                         @Value("#{'${tg-bot.admins}'.split(',')}") List<String> admins, StatisticsService statisticsService) {
        super(botToken);
        this.commandContainer = new CommandContainer(
                new SendBotMessageServiceImpl(this),
                telegramUserService,
                groupClient,
                groupSubService, admins, statisticsService);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            String tgUsername = update.getMessage().getFrom().getUserName();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();

                commandContainer.retrieveCommand(commandIdentifier, tgUsername).execute(update);
            } else {
                commandContainer.retrieveCommand(NO.getCommandName(), tgUsername).execute(update);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }
}
