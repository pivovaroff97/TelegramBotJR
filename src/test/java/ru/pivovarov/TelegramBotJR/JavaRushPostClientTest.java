package ru.pivovarov.TelegramBotJR;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.pivovarov.TelegramBotJR.jrclient.JavaRushPostClient;
import ru.pivovarov.TelegramBotJR.jrclient.JavaRushPostClientImpl;
import ru.pivovarov.TelegramBotJR.jrclient.dto.PostInfo;

import java.util.List;

@DisplayName("Integration-level testing for JavaRushPostClient")
public class JavaRushPostClientTest {
    private final JavaRushPostClient postClient = new JavaRushPostClientImpl("https://javarush.com/api/1.0/rest/posts");

    @Test
    public void shouldProperlyGetNew15Posts() {
        //when
        List<PostInfo> newPosts = postClient.findNewPosts(30, 2935);

        //then
        Assertions.assertEquals(15, newPosts.size());
    }
}
