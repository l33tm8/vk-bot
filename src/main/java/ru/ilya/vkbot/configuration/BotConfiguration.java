package ru.ilya.vkbot.configuration;

import com.google.gson.GsonBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.ilya.vkbot.models.Event;
import ru.ilya.vkbot.serialization.EventDeserializer;

@Configuration
public class BotConfiguration {


    @Bean
    public GsonBuilder gsonBuilder() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Event.class, new EventDeserializer());
        return gsonBuilder;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }
}
