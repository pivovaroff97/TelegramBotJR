package ru.pivovarov.TelegramBotJR.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.pivovarov.TelegramBotJR.jrclient.JavaRushGroupClient;
import ru.pivovarov.TelegramBotJR.jrclient.dto.GroupRequestArgs;
import ru.pivovarov.TelegramBotJR.service.GroupSubService;
import ru.pivovarov.TelegramBotJR.service.SendBotMessageService;
import ru.pivovarov.TelegramBotJR.jrclient.dto.GroupDiscussionInfo;
import ru.pivovarov.TelegramBotJR.repository.entity.GroupSub;

import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static ru.pivovarov.TelegramBotJR.command.CommandName.ADD_GROUP_SUB;;

public class AddGroupSubCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final JavaRushGroupClient javaRushGroupClient;
    private final GroupSubService groupSubService;

    public AddGroupSubCommand(SendBotMessageService sendBotMessageService, JavaRushGroupClient javaRushGroupClient,
                              GroupSubService groupSubService) {
        this.sendBotMessageService = sendBotMessageService;
        this.javaRushGroupClient = javaRushGroupClient;
        this.groupSubService = groupSubService;
    }
    @Override
    public void execute(Update update) {
        String message = update.getMessage().getText();
        String chatId = String.valueOf(update.getMessage().getChatId());
        if (message.equalsIgnoreCase(ADD_GROUP_SUB.getCommandName())) {
            sendGroupIdList(chatId);
            return;
        }
        String groupId = message.split(SPACE)[1];
        if (isNumeric(groupId)) {
            GroupDiscussionInfo groupById = javaRushGroupClient.getGroupById(Integer.parseInt(groupId));
            if (isNull(groupById.getId())) {
                sendGroupNotFound(chatId, groupId);
            }
            GroupSub savedGroupSub = groupSubService.save(chatId, groupById);
            sendBotMessageService.sendMessage(chatId, "Подписал на группу " + savedGroupSub.getTitle());
        } else {
            sendGroupNotFound(chatId, groupId);
        }
    }

    private void sendGroupNotFound(String chatId, String groupId) {
        String groupNotFoundMessage = "Нет группы с ID = \"%s\"";
        sendBotMessageService.sendMessage(chatId, String.format(groupNotFoundMessage, groupId));
    }

    private void sendGroupIdList(String chatId) {
        String groupIds = javaRushGroupClient.getGroupList(GroupRequestArgs.builder().build()).stream()
                .map(group -> String.format("%s - %s \n", group.getTitle(), group.getId()))
                .collect(Collectors.joining());

        String message = "Чтобы подписаться на группу - передай комадну вместе с ID группы. \n" +
                "Например: /addGroupSub 16. \n\n" +
                "я подготовил список всех групп - выберай какую хочешь :) \n\n" +
                "имя группы - ID группы \n\n" +
                "%s";

        sendBotMessageService.sendMessage(chatId, String.format(message, groupIds));
    }
}
