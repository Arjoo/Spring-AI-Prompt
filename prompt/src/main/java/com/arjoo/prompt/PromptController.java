package com.arjoo.prompt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/prompt")
@Slf4j
public class PromptController {

    private final ChatClient chatClient;

    @Autowired
    public PromptController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/joke")
    public String joke(@RequestParam(value="message", defaultValue = "Tell me a joke") String message) {
        return chatClient.prompt().user(message).call().content();
    }

    @GetMapping("/custom")
    public String custom(@RequestParam(value="message", defaultValue = "Give any custom message") String message) {
        ChatResponse chatResponse = chatClient.prompt().user(message).call().chatResponse();
        log.info(String.valueOf(chatResponse));
        return chatResponse.getResult().getOutput().getContent();
    }

    @GetMapping("/movie")
    public Movie movie(@RequestParam(value="movie-name", defaultValue = "Give any movie name") String movieName) {
        Movie chatResponse = chatClient.prompt().user(movieName).call().entity(Movie.class);
        log.info(String.valueOf(chatResponse));
        return chatResponse;
    }

    @GetMapping("/actor")
    public Actor actor(@RequestParam(value="actor-name", defaultValue = "Give list of movie name of a actor") String actorName) {
        Actor chatResponse = chatClient.prompt().user(actorName).call().entity(Actor.class);
        log.info(String.valueOf(chatResponse));
        return chatResponse;
    }
}
