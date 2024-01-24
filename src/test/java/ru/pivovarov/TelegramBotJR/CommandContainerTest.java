package ru.pivovarov.TelegramBotJR;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.pivovarov.TelegramBotJR.command.Command;
import ru.pivovarov.TelegramBotJR.command.CommandContainer;
import ru.pivovarov.TelegramBotJR.command.CommandName;
import ru.pivovarov.TelegramBotJR.command.UnknownCommand;
import ru.pivovarov.TelegramBotJR.jrclient.JavaRushGroupClient;
import ru.pivovarov.TelegramBotJR.service.GroupSubService;
import ru.pivovarov.TelegramBotJR.service.SendBotMessageService;
import ru.pivovarov.TelegramBotJR.service.StatisticsService;
import ru.pivovarov.TelegramBotJR.service.TelegramUserService;

import java.util.Arrays;
import java.util.Collections;

@DisplayName("Unit-level testing for CommandContainer")
public class CommandContainerTest {

    private CommandContainer commandContainer;

    @BeforeEach
    public void init() {
        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        JavaRushGroupClient groupClient = Mockito.mock(JavaRushGroupClient.class);
        GroupSubService groupSubService = Mockito.mock(GroupSubService.class);
        StatisticsService statisticsService = Mockito.mock(StatisticsService.class);
        commandContainer = new CommandContainer(sendBotMessageService,
                telegramUserService,
                groupClient,
                groupSubService,
                Collections.singletonList("usernametest"), statisticsService);
    }

    @Test
    public void retrieveCommand() {
        //when-then
        Arrays.stream(CommandName.values())
                .forEach(commandName -> {
                    Command command = commandContainer.retrieveCommand(commandName.getCommandName(), "usernametest");
                    Assertions.assertNotEquals(UnknownCommand.class, command.getClass());
                });
    }

    @Test
    public void returnUnknownCommand() {
        String unknownCommand = "/ggwpTest";
        Command command = commandContainer.retrieveCommand(unknownCommand, "usernametest");
        Assertions.assertEquals(UnknownCommand.class, command.getClass());
    }
}
