package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.CreateMessageRequest;
import org.example.api.ApiService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.controller.Api.PREFIX;
import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping(PREFIX)
@RequiredArgsConstructor
public class ApiController {
    private final ApiService apiService;

    @PostMapping("/send")
    @ResponseStatus(CREATED)
    public void send(@RequestBody CreateMessageRequest request) {
        apiService.sendMessage(request);
    }

    @GetMapping("/last100Messages")
    public List<String> getLast100Messages() {
        return apiService.getLast100Messages();
    }

    @GetMapping("/messages")
    public List<String> getMessages(@RequestParam(required = false, defaultValue = "0") int offset,
                                    @RequestParam(required = false, defaultValue = "20") int limit) {
        return apiService.getMessages(offset, limit);
    }
}
