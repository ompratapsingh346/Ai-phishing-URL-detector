package com.project.phishingdetector.controller;

import com.project.phishingdetector.model.ScanHistory;
import com.project.phishingdetector.repository.ScanHistoryRepository;
import com.project.phishingdetector.service.MLService;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class ScanController {

    private final ScanHistoryRepository repository;

    public ScanController(ScanHistoryRepository repository) {

        this.repository = repository;
    }

    // Scan URL API

    @PostMapping("/scan")
    public Map<String, Object> scanUrl(
            @RequestBody Map<String, String> request
    ) {

        String url = request.get("url");

        Map<String, Object> response =
                MLService.predict(url);

        // Save into MongoDB

        ScanHistory history = new ScanHistory(

                url,

                response.get("result").toString(),

                LocalDateTime.now()
        );

        repository.save(history);

        return response;
    }

    // History API

    @GetMapping("/history")
    public List<ScanHistory> getHistory() {

        return repository.findAll();
    }
}