package ru.pivovarov.TelegramBotJR.service;

import ru.pivovarov.TelegramBotJR.jrclient.dto.GroupDiscussionInfo;
import ru.pivovarov.TelegramBotJR.repository.entity.GroupSub;

public interface GroupSubService {
    GroupSub save(String chatId, GroupDiscussionInfo groupDiscussionInfo);
}
