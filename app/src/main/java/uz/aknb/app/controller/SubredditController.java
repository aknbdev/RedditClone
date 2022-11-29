package uz.aknb.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.aknb.app.dto.SubredditDto;
import uz.aknb.app.service.SubredditService;

@Slf4j
@RestController
@RequestMapping("/api/subreddit")
public class SubredditController {

    private final SubredditService service;

    public SubredditController(SubredditService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody SubredditDto dto) {

        log.info("Subreddit create request name: {}", dto.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @GetMapping
    public ResponseEntity<?> getAll() {

        log.info("Subreddit get all request");
        return ResponseEntity.ok(service.getAll());
    }
}
