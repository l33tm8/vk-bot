package ru.ilya.vkbot.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EventObjectNewMessage extends EventObject {
    private Message message;
}
