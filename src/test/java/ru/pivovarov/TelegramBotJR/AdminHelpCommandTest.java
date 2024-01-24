package ru.pivovarov.TelegramBotJR;

import org.junit.jupiter.api.DisplayName;
import ru.pivovarov.TelegramBotJR.command.AdminHelpCommand;
import ru.pivovarov.TelegramBotJR.command.Command;

import static ru.pivovarov.TelegramBotJR.command.AdminHelpCommand.ADMIN_HELP_MESSAGE;
import static ru.pivovarov.TelegramBotJR.command.CommandName.ADMIN_HELP;

@DisplayName("Unit-level testing for AdminHelpCommand")
public class AdminHelpCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return ADMIN_HELP.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return ADMIN_HELP_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new AdminHelpCommand(sendBotMessageService);
    }
}
