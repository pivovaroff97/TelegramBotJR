package ru.pivovarov.TelegramBotJR;

import ru.pivovarov.TelegramBotJR.command.Command;
import ru.pivovarov.TelegramBotJR.command.CommandName;
import ru.pivovarov.TelegramBotJR.command.StatCommand;

public class StatCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return CommandName.STAT.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return StatCommand.STAT_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new StatCommand(sendBotMessageService, telegramUserService);
    }
}
