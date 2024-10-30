package ru.ilya.vkbot;

import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ilya.vkbot.models.Event;
import ru.ilya.vkbot.serialization.EventDeserializer;

@SpringBootTest
class VkBotApplicationTests {

    @Test
    void eventDeserializerTest() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Event.class, new EventDeserializer());
        String json = "{ \"type\": \"confirmation\", \"group_id\": 1 }";
        Event event = gsonBuilder.create().fromJson(json, Event.class);
        Assertions.assertNotNull(event);
        Assertions.assertEquals("confirmation", event.getType());
        Assertions.assertEquals(1, event.getGroupId());
    }

}
