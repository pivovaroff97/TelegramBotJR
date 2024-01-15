package ru.pivovarov.TelegramBotJR.command;

import com.google.common.collect.ImmutableMap;
import ru.pivovarov.TelegramBotJR.jrclient.JavaRushGroupClient;
import ru.pivovarov.TelegramBotJR.service.GroupSubService;
import ru.pivovarov.TelegramBotJR.service.SendBotMessageService;
import ru.pivovarov.TelegramBotJR.service.TelegramUserService;

import static ru.pivovarov.TelegramBotJR.command.CommandName.*;

public class CommandContainer {
    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService,
                            JavaRushGroupClient javaRushGroupClient, GroupSubService groupSubService) {

        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService, telegramUserService))
                .put(STOP.getCommandName(), new StopCommand(sendBotMessageService, telegramUserService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService))
                .put(STAT.getCommandName(), new StatCommand(sendBotMessageService, telegramUserService))
                .put(ADD_GROUP_SUB.getCommandName(), new AddGroupSubCommand(sendBotMessageService, javaRushGroupClient, groupSubService))
                .put(LIST_GROUP_SUB.getCommandName(), new GroupSubListCommand(sendBotMessageService, telegramUserService))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}
