package ru.pivovarov.TelegramBotJR;

import ru.pivovarov.TelegramBotJR.command.Command;
import ru.pivovarov.TelegramBotJR.command.CommandName;
import ru.pivovarov.TelegramBotJR.command.StartCommand;

public class StartCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return CommandName.START.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return StartCommand.START_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new StartCommand(sendBotMessageService, telegramUserService);
    }
}
