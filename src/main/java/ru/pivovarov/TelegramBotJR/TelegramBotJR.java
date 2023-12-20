package ru.pivovarov.TelegramBotJR;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.pivovarov.TelegramBotJR.command.CommandContainer;
import ru.pivovarov.TelegramBotJR.service.SendBotMessageServiceImpl;

import static ru.pivovarov.TelegramBotJR.command.CommandName.NO;

@Component
public class TelegramBotJR extends TelegramLongPollingBot {

    @Value("${tg-bot.username}")
    private String username;

    private final CommandContainer commandContainer;

    public static String COMMAND_PREFIX = "/";

    public TelegramBotJR(@Value("${tg-bot.token}") String botToken) {
        super(botToken);
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this));
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();

                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            } else {
                commandContainer.retrieveCommand(NO.getCommandName()).execute(update);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }
}
