package ru.ilya.vkbot.models;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VkApiError {
    @SerializedName(value = "error_code")
    private int code;

    @SerializedName(value = "error_msg")
    private String message;
}
