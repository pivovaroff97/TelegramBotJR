package ru.pivovarov.TelegramBotJR;

import ru.pivovarov.TelegramBotJR.command.Command;
import ru.pivovarov.TelegramBotJR.command.UnknownCommand;

public class UnknownCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return "it is UnknownCommand 100%";
    }

    @Override
    String getCommandMessage() {
        return UnknownCommand.UNKNOWN_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new UnknownCommand(sendBotMessageService);
    }
}
