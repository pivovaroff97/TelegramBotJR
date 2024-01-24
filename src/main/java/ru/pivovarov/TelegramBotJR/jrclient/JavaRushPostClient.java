package ru.pivovarov.TelegramBotJR.jrclient;

import ru.pivovarov.TelegramBotJR.jrclient.dto.PostInfo;

import java.util.List;

public interface JavaRushPostClient {

    List<PostInfo> findNewPosts(Integer groupId, Integer lastPostId);
}
