package ru.ilya.vkbot.service;

import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.ilya.vkbot.models.Event;
import ru.ilya.vkbot.models.EventObjectNewMessage;
import ru.ilya.vkbot.models.Message;

import java.util.Random;

@Service
@Slf4j
public class BotService {

    @Value("${bot.confirmation.code}")
    private String confirmationCode;

    @Value("${bot.token}")
    private String token;

    private final GsonBuilder gsonBuilder;

    public BotService(GsonBuilder gsonBuilder) {
        this.gsonBuilder = gsonBuilder;
    }

    public String handleEvent(String jsonEvent) {
        Event event = gsonBuilder.create().fromJson(jsonEvent, Event.class);
        if ("confirmation".equals(event.getType())) {
            log.info("Confirmation received");
            return confirmationCode;
        }
        if ("message_new".equals(event.getType())) {

            EventObjectNewMessage object = (EventObjectNewMessage) event.getObject();
            Message message = object.getMessage();
            log.info("New message received from id {}", message.getId());
            sendMessage(message.getText(), message.getFromId());
        }

        return "ok";
    }

    private void sendMessage(String text, Integer userId) {
        String url = "https://api.vk.com/method/messages.send";
        String messageText = "Вы сказали: " + text;
        long randomId = new Random().nextLong();

        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("access_token", token);
        map.add("peer_id", userId.toString());
        map.add("message", messageText);
        map.add("random_id", Long.toString(randomId));
        map.add("v", "5.199");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = template.postForEntity(url, request, String.class);
        log.info("Message sent: {}", response.getBody());
    }
}
