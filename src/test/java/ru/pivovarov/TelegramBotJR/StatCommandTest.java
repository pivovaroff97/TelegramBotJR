package ru.pivovarov.TelegramBotJR;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.pivovarov.TelegramBotJR.command.Command;
import ru.pivovarov.TelegramBotJR.command.CommandName;
import ru.pivovarov.TelegramBotJR.command.StatCommand;
import ru.pivovarov.TelegramBotJR.jrclient.dto.GroupStatDTO;
import ru.pivovarov.TelegramBotJR.jrclient.dto.StatisticDTO;
import ru.pivovarov.TelegramBotJR.service.SendBotMessageService;
import ru.pivovarov.TelegramBotJR.service.StatisticsService;

import java.util.Collections;

import static ru.pivovarov.TelegramBotJR.AbstractCommandTest.prepareUpdate;
import static ru.pivovarov.TelegramBotJR.command.StatCommand.STAT_MESSAGE;

@DisplayName("Unit-level testing for StatCommand")
public class StatCommandTest {

    private SendBotMessageService sendBotMessageService;
    private StatisticsService statisticsService;
    private Command statCommand;

    @BeforeEach
    public void init() {
        sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        statisticsService = Mockito.mock(StatisticsService.class);
        statCommand = new StatCommand(sendBotMessageService, statisticsService);
    }

    @Test
    public void shouldProperlySendMessage() {
        //given
        Long chatId = 1234567L;
        GroupStatDTO groupDto = new GroupStatDTO(1, "group", 1);
        StatisticDTO statisticDTO = new StatisticDTO(1, 1, Collections.singletonList(groupDto), 2.5);
        Mockito.when(statisticsService.countBotStatistic())
                .thenReturn(statisticDTO);

        //when
        statCommand.execute(prepareUpdate(chatId, CommandName.STAT.getCommandName()));

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, String.format(STAT_MESSAGE,
                statisticDTO.getActiveUserCount(),
                statisticDTO.getInactiveUserCount(),
                statisticDTO.getAverageGroupCountByUser(),
                String.format("%s (id = %s) - %s подписчиков", groupDto.getTitle(), groupDto.getId(), groupDto.getActiveUserCount())));
    }
}
