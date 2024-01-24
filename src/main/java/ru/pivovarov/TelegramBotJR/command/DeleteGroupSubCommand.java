package ru.pivovarov.TelegramBotJR.command;

import jakarta.ws.rs.NotFoundException;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.pivovarov.TelegramBotJR.repository.entity.GroupSub;
import ru.pivovarov.TelegramBotJR.repository.entity.TelegramUser;
import ru.pivovarov.TelegramBotJR.service.GroupSubService;
import ru.pivovarov.TelegramBotJR.service.SendBotMessageService;
import ru.pivovarov.TelegramBotJR.service.TelegramUserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import static ru.pivovarov.TelegramBotJR.command.CommandName.DELETE_GROUP_SUB;

public class DeleteGroupSubCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final GroupSubService groupSubService;

    public DeleteGroupSubCommand(SendBotMessageService sendBotMessageService, GroupSubService groupSubService,
                                 TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.groupSubService = groupSubService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        String message = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        if (message.equalsIgnoreCase(DELETE_GROUP_SUB.getCommandName())) {
            sendGroupIdList(chatId);
            return;
        }
        String groupId = message.split(SPACE)[1];
        if (isNumeric(groupId)) {
            Optional<GroupSub> optionalGroupSub = groupSubService.findById(Integer.parseInt(groupId));
            if (optionalGroupSub.isPresent()) {
                GroupSub groupSub = optionalGroupSub.get();
                TelegramUser telegramUser = telegramUserService.findByChatId(chatId).orElseThrow(NotFoundException::new);
                groupSub.getUsers().remove(telegramUser);
                groupSubService.save(groupSub);
                sendBotMessageService.sendMessage(chatId, format("Удалил подписку на группу: %s", groupSub.getTitle()));
            } else {
                sendBotMessageService.sendMessage(chatId, "Не нашел такой группы =/");
            }
        } else {
            sendBotMessageService.sendMessage(chatId, "неправильный формат ID группы.\n " +
                    "ID должно быть целым положительным числом");
        }
    }

    private void sendGroupIdList(Long chatId) {
        String message;
        List<GroupSub> groupSubs = telegramUserService.findByChatId(chatId)
                .orElseThrow(NotFoundException::new)
                .getGroupSubs();
        if (CollectionUtils.isEmpty(groupSubs)) {
            message = "Пока нет подписок на группы. Чтобы добавить подписку напиши /addGroupSub";
        } else {
            String userGroupSubData = groupSubs.stream()
                    .map(group -> format("%s - %s \n", group.getTitle(), group.getId()))
                    .collect(Collectors.joining());
            message = String.format("""
                    Чтобы удалить подписку на группу - передай комадну вместе с ID группы.\s
                    Например: /deleteGroupSub 16\s

                    я подготовил список всех групп, на которые ты подписан)\s

                    имя группы - ID группы\s

                    %s""", userGroupSubData);
        }

        sendBotMessageService.sendMessage(chatId, message);
    }
}
