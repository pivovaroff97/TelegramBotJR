package ru.pivovarov.TelegramBotJR;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static ru.pivovarov.TelegramBotJR.command.CommandName.DELETE_GROUP_SUB;
import static ru.pivovarov.TelegramBotJR.AbstractCommandTest.prepareUpdate;

import org.junit.jupiter.api.DisplayName;
import ru.pivovarov.TelegramBotJR.command.Command;
import ru.pivovarov.TelegramBotJR.command.DeleteGroupSubCommand;
import ru.pivovarov.TelegramBotJR.repository.entity.GroupSub;
import ru.pivovarov.TelegramBotJR.repository.entity.TelegramUser;
import ru.pivovarov.TelegramBotJR.service.GroupSubService;
import ru.pivovarov.TelegramBotJR.service.SendBotMessageService;
import ru.pivovarov.TelegramBotJR.service.TelegramUserService;

@DisplayName("Unit-level testing for DeleteGroupSubCommand")
public class DeleteGroupSubCommandTest {
    private Command command;
    private SendBotMessageService sendBotMessageService;
    GroupSubService groupSubService;
    TelegramUserService telegramUserService;


    @BeforeEach
    public void init() {
        sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        groupSubService = Mockito.mock(GroupSubService.class);
        telegramUserService = Mockito.mock(TelegramUserService.class);

        command = new DeleteGroupSubCommand(sendBotMessageService, groupSubService, telegramUserService);
    }

    @Test
    public void shouldProperlyReturnEmptySubscriptionList() {
        //given
        Long chatId = 23456L;
        Update update = prepareUpdate(chatId, DELETE_GROUP_SUB.getCommandName());

        Mockito.when(telegramUserService.findByChatId(chatId))
                .thenReturn(Optional.of(new TelegramUser()));

        String expectedMessage = "Пока нет подписок на группы. Чтобы добавить подписку напиши /addGroupSub";

        //when
        command.execute(update);

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, expectedMessage);
    }

    @Test
    public void shouldProperlyReturnSubscriptionLit() {
        //given
        Long chatId = 23456L;
        Update update = prepareUpdate(chatId, DELETE_GROUP_SUB.getCommandName());
        TelegramUser telegramUser = new TelegramUser();
        GroupSub gs1 = new GroupSub();
        gs1.setId(123);
        gs1.setTitle("GS1 Title");
        telegramUser.setGroupSubs(singletonList(gs1));
        Mockito.when(telegramUserService.findByChatId(chatId))
                .thenReturn(Optional.of(telegramUser));

        String expectedMessage = """
                Чтобы удалить подписку на группу - передай комадну вместе с ID группы.\s
                Например: /deleteGroupSub 16\s

                я подготовил список всех групп, на которые ты подписан)\s

                имя группы - ID группы\s

                GS1 Title - 123\s
                """;

        //when
        command.execute(update);

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, expectedMessage);
    }

    @Test
    public void shouldRejectByInvalidGroupId() {
        //given
        Long chatId = 23456L;
        Update update = prepareUpdate(chatId, String.format("%s %s", DELETE_GROUP_SUB.getCommandName(), "groupSubId"));
        TelegramUser telegramUser = new TelegramUser();
        GroupSub gs1 = new GroupSub();
        gs1.setId(123);
        gs1.setTitle("GS1 Title");
        telegramUser.setGroupSubs(singletonList(gs1));
        Mockito.when(telegramUserService.findByChatId(chatId))
                .thenReturn(Optional.of(telegramUser));

        String expectedMessage = "неправильный формат ID группы.\n " +
                "ID должно быть целым положительным числом";

        //when
        command.execute(update);

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, expectedMessage);
    }

    @Test
    public void shouldProperlyDeleteByGroupId() {
        //given

        /// prepare update object
        Long chatId = 23456L;
        Integer groupId = 1234;
        Update update = prepareUpdate(chatId, String.format("%s %s", DELETE_GROUP_SUB.getCommandName(), groupId));


        GroupSub gs1 = new GroupSub();
        gs1.setId(123);
        gs1.setTitle("GS1 Title");
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId(chatId);
        telegramUser.setGroupSubs(singletonList(gs1));
        ArrayList<TelegramUser> users = new ArrayList<>();
        users.add(telegramUser);
        gs1.setUsers(users);
        Mockito.when(groupSubService.findById(groupId)).thenReturn(Optional.of(gs1));
        Mockito.when(telegramUserService.findByChatId(chatId))
                .thenReturn(Optional.of(telegramUser));

        String expectedMessage = "Удалил подписку на группу: GS1 Title";

        //when
        command.execute(update);

        //then
        users.remove(telegramUser);
        Mockito.verify(groupSubService).save(gs1);
        Mockito.verify(sendBotMessageService).sendMessage(chatId, expectedMessage);
    }

    @Test
    public void shouldDoesNotExistByGroupId() {
        //given
        Long chatId = 23456L;
        Integer groupId = 1234;
        Update update = prepareUpdate(chatId, String.format("%s %s", DELETE_GROUP_SUB.getCommandName(), groupId));


        Mockito.when(groupSubService.findById(groupId)).thenReturn(Optional.empty());

        String expectedMessage = "Не нашел такой группы =/";

        //when
        command.execute(update);

        //then
        Mockito.verify(groupSubService).findById(groupId);
        Mockito.verify(sendBotMessageService).sendMessage(chatId, expectedMessage);
    }
}
