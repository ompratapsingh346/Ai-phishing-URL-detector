package com.project.phishingdetector.controller;

import com.project.phishingdetector.model.ScanHistory;
import com.project.phishingdetector.repository.ScanHistoryRepository;
import com.project.phishingdetector.service.MLService;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class ScanController {

    private final ScanHistoryRepository repository;

    public ScanController(ScanHistoryRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/scan")
    public Map<String, String> scanUrl(
            @RequestBody Map<String, String> request
    ) {

        // Get URL from frontend
        String url = request.get("url");

        // AI/ML prediction
        String result = MLService.predict(url);

        // Save scan history into MongoDB
        ScanHistory history = new ScanHistory(
                url,
                result,
                LocalDateTime.now()
        );

        repository.save(history);

        // Response to frontend/postman
        Map<String, String> response = new HashMap<>();

        response.put("url", url);
        response.put("result", result);

        return response;
    }
}