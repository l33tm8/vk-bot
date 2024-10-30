package ru.ilya.vkbot.models;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Event {

    @SerializedName(value = "type")
    private String type;

    @SerializedName(value = "object")
    private EventObject object;

    @SerializedName(value = "group_id")
    private Long groupId;
}
