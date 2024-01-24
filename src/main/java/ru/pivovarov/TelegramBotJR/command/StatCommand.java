package ru.pivovarov.TelegramBotJR.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.pivovarov.TelegramBotJR.jrclient.dto.StatisticDTO;
import ru.pivovarov.TelegramBotJR.service.SendBotMessageService;
import ru.pivovarov.TelegramBotJR.service.StatisticsService;

import java.util.stream.Collectors;

@AdminCommand
public class StatCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final StatisticsService statisticsService;

    public final static String STAT_MESSAGE = """
            ✨<b>Подготовил статистику</b>✨
            - Количество активных пользователей: %s
            - Количество неактивных пользователей: %s
            - Среднее количество групп на одного пользователя: %s

            <b>Информация по активным группам</b>:
            %s""";

    @Autowired
    public StatCommand(SendBotMessageService sendBotMessageService, StatisticsService statisticsService) {
        this.sendBotMessageService = sendBotMessageService;
        this.statisticsService = statisticsService;
    }

    @Override
    public void execute(Update update) {
        StatisticDTO statisticDTO = statisticsService.countBotStatistic();

        String collectedGroups = statisticDTO.getGroupStatDTOs().stream()
                .map(it -> String.format("%s (id = %s) - %s подписчиков", it.getTitle(), it.getId(), it.getActiveUserCount()))
                .collect(Collectors.joining("\n"));

        sendBotMessageService.sendMessage(update.getMessage().getChatId(), String.format(STAT_MESSAGE,
                statisticDTO.getActiveUserCount(),
                statisticDTO.getInactiveUserCount(),
                statisticDTO.getAverageGroupCountByUser(),
                collectedGroups));
    }
}
