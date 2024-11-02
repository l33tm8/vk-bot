package ru.ilya.vkbot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ilya.vkbot.exception.VkApiException;
import ru.ilya.vkbot.models.Event;
import ru.ilya.vkbot.models.EventObjectNewMessage;
import ru.ilya.vkbot.models.Message;
import ru.ilya.vkbot.service.BotService;

@SpringBootTest
public class BotServiceTest {

    private final BotService botService;

    @Value("${bot.confirmation.code}")
    private String confirmationCode;

    @Autowired
    public BotServiceTest(BotService botService) {
        this.botService = botService;
    }

    @Test
    public void sendMessageTest() {
        Assertions.assertThrows(VkApiException.class, () -> botService.sendMessage("test", -1));
        Assertions.assertDoesNotThrow(() -> botService.sendMessage("test", 173350170));
    }

    @Test
    public void handleEventConfirmTest() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Event event = new Event("confirmation", null, 1L);
        String json = gson.toJson(event, Event.class);
        Assertions.assertEquals(confirmationCode, botService.handleEvent(json));
    }

    @Test
    public void handleEventSendMessageTest() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Message message = new Message();
        message.setFromId(177350170);
        message.setText("Hello World");
        EventObjectNewMessage object = new EventObjectNewMessage();
        object.setMessage(message);
        Event event = new Event("message_new", object, 1L);
        String json = gson.toJson(event);
        Assertions.assertEquals("ok", botService.handleEvent(json));
    }
}
