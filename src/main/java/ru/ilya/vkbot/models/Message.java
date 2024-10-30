package ru.ilya.vkbot.models;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Message {
    private Integer id;
    private String text;
    private Integer date;

    @SerializedName(value = "from_id")
    private Integer fromId;
}
