package ru.ilya.vkbot.exception;

public class VkApiException extends Exception {
    public VkApiException(String message) {
        super(message);
    }

    public VkApiException(int code, String message) {
        super("VK API exception: code: " + code + " message: " + message);
    }
}
