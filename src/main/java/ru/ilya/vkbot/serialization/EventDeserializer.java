package ru.ilya.vkbot.serialization;

import com.google.gson.*;
import ru.ilya.vkbot.models.Event;
import ru.ilya.vkbot.models.EventObject;
import ru.ilya.vkbot.models.EventObjectNewMessage;

import java.lang.reflect.Type;


public class EventDeserializer implements JsonDeserializer<Event> {
    @Override
    public Event deserialize(JsonElement jsonElement,
                             Type typeOfT,
                             JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        Long groupId = jsonObject.get("group_id").getAsLong();
        EventObject eventObject = null;
        if (jsonObject.has("object")) {
            JsonElement objectElement = jsonObject.get("object");

            if (type.equals("message_new")) {
                eventObject = jsonDeserializationContext.deserialize(objectElement, EventObjectNewMessage.class);
            }
        }
        return new Event(type, eventObject, groupId);

    }
}

