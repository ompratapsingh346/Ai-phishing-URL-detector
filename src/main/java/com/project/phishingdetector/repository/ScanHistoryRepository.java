package com.project.phishingdetector.repository;

import com.project.phishingdetector.model.ScanHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScanHistoryRepository
        extends MongoRepository<ScanHistory, String> {

}