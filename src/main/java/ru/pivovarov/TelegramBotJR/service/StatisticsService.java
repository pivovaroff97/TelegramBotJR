package ru.pivovarov.TelegramBotJR.service;

import ru.pivovarov.TelegramBotJR.jrclient.dto.StatisticDTO;

public interface StatisticsService {
    StatisticDTO countBotStatistic();
}
