package com.project.phishingdetector.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "scan_history")
public class ScanHistory {

    @Id
    private String id;

    private String url;

    private String result;

    private LocalDateTime scannedAt;

    public ScanHistory() {
    }

    public ScanHistory(String url, String result, LocalDateTime scannedAt) {
        this.url = url;
        this.result = result;
        this.scannedAt = scannedAt;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDateTime getScannedAt() {
        return scannedAt;
    }

    public void setScannedAt(LocalDateTime scannedAt) {
        this.scannedAt = scannedAt;
    }
}