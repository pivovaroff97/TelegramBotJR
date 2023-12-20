package ru.pivovarov.TelegramBotJR;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.pivovarov.TelegramBotJR.service.SendBotMessageService;
import ru.pivovarov.TelegramBotJR.service.SendBotMessageServiceImpl;

@DisplayName("Unit-level test for SendBotMessageService")
public class SendBotMessageServiceTest {

    private TelegramBotJR telegramBotJR;
    private SendBotMessageService sendBotMessageService;

    @BeforeEach
    public void init() {
        telegramBotJR = Mockito.mock(TelegramBotJR.class);
        sendBotMessageService = new SendBotMessageServiceImpl(telegramBotJR);
    }

    @Test
    public void sendMessage() throws TelegramApiException {
        String chatId = "testChatId";
        String message = "testMessage";
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.enableHtml(true);
        //when
        sendBotMessageService.sendMessage(chatId, message);
        //then
        Mockito.verify(telegramBotJR.execute(sendMessage));
    }
}
