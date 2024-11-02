package ru.ilya.vkbot.service;

import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.ilya.vkbot.exception.VkApiException;
import ru.ilya.vkbot.models.Event;
import ru.ilya.vkbot.models.EventObjectNewMessage;
import ru.ilya.vkbot.models.Message;
import ru.ilya.vkbot.models.VkApiError;


@Service
@Slf4j
public class BotService {

    @Value("${bot.confirmation.code}")
    private String confirmationCode;

    @Value("${bot.token}")
    private String token;

    private final GsonBuilder gsonBuilder;
    private final RestTemplate restTemplate;

    public BotService(GsonBuilder gsonBuilder, RestTemplate restTemplate) {
        this.gsonBuilder = gsonBuilder;
        this.restTemplate = restTemplate;
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
            log.info("New message received from id {}", message.getFromId());
            try {
                sendMessage(message.getText(), message.getFromId());
            }
            catch (VkApiException e) {
                log.error(e.getMessage());
            }
        }

        return "ok";
    }

    public void sendMessage(String text, Integer userId) throws VkApiException {
        String url = "https://api.vk.com/method/messages.send";
        String messageText = "Вы сказали: " + text;
        int randomId = 0;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("access_token", token);
        map.add("peer_id", userId.toString());
        map.add("message", messageText);
        map.add("random_id", Long.toString(randomId));
        map.add("v", "5.199");

        try {
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            String body = response.getBody();
            if (body == null || body.contains("error_code")) {
                if (body == null)
                    throw new VkApiException("No body response from server");
                body = body.substring(9, body.length() - 1);
                VkApiError apiError = gsonBuilder.create().fromJson(body, VkApiError.class);
                throw new VkApiException(apiError.getCode(), apiError.getMessage());
            }

            log.info("Message sent: {}", body);
        }
        catch (RestClientException e) {
            log.error(e.getMessage());
        }
    }
}
