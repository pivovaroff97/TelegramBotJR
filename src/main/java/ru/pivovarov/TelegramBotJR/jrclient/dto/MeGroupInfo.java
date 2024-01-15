package ru.pivovarov.TelegramBotJR.jrclient.dto;

import lombok.Data;

@Data
public class MeGroupInfo {

    private MeGroupInfoStatus status;
    private Integer userGroupId;
}
