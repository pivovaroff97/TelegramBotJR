package ru.pivovarov.TelegramBotJR.jrclient;

import kong.unirest.GenericType;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.pivovarov.TelegramBotJR.jrclient.dto.GroupCountRequestArgs;
import ru.pivovarov.TelegramBotJR.jrclient.dto.GroupDiscussionInfo;
import ru.pivovarov.TelegramBotJR.jrclient.dto.GroupInfo;
import ru.pivovarov.TelegramBotJR.jrclient.dto.GroupRequestArgs;

import java.util.List;

@Component
public class JavaRushGroupClientImpl implements JavaRushGroupClient {

    private final String javarushApiGroupPath;

    public JavaRushGroupClientImpl(@Value("${javarush.api.path}") String javarushApi) {
        this.javarushApiGroupPath = javarushApi + "/groups";
    }
    @Override
    public List<GroupInfo> getGroupList(GroupRequestArgs requestArgs) {
        return Unirest.get(javarushApiGroupPath)
                .queryString(requestArgs.populateQueries())
                .asObject(new GenericType<List<GroupInfo>>() {
                })
                .getBody();
    }

    @Override
    public List<GroupDiscussionInfo> getGroupDiscussionList(GroupRequestArgs requestArgs) {
        return Unirest.get(javarushApiGroupPath)
                .queryString(requestArgs.populateQueries())
                .asObject(new GenericType<List<GroupDiscussionInfo>>(){})
                .getBody();
    }

    @Override
    public Integer getGroupCount(GroupCountRequestArgs countRequestArgs) {
        return Integer.valueOf(
                Unirest.get(String.format("%s/count", javarushApiGroupPath))
                        .queryString(countRequestArgs.populateQueries())
                        .asString()
                        .getBody()
        );
    }

    @Override
    public GroupDiscussionInfo getGroupById(Integer id) {
        return Unirest.get(String.format("%s/group%s", javarushApiGroupPath, id.toString()))
                .asObject(GroupDiscussionInfo.class)
                .getBody();
    }
}
