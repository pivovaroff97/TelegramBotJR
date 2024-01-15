package ru.pivovarov.TelegramBotJR.jrclient.dto;

import lombok.Data;

@Data
public class UserDiscussionInfo {
    private Boolean isBookMarked;
    private Integer lastTime;
    private Integer newCommentsCount;
}
