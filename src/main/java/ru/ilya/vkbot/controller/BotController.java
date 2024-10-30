package ru.ilya.vkbot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ilya.vkbot.service.BotService;

@RestController
@RequestMapping("/callback")
public class BotController {

    private final BotService botService;

    public BotController(BotService botService) {
        this.botService = botService;
    }

    @PostMapping
    public ResponseEntity<String> handleEvent(@RequestBody String event) {
        return ResponseEntity.ok(botService.handleEvent(event));
    }
}
