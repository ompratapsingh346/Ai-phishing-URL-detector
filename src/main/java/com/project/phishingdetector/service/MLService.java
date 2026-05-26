package com.project.phishingdetector.service;

public class MLService {

    public static String predict(String url) {

        double[] features =
                FeatureExtractor.extractFeatures(url);

        double length = features[0];

        double dots = features[1];

        double hasHttps = features[2];

        double hyphens = features[3];

        // Simple ML-like prediction logic

        int score = 0;

        if(length > 30) {
            score++;
        }

        if(dots > 3) {
            score++;
        }

        if(hasHttps == 0) {
            score++;
        }

        if(hyphens > 2) {
            score++;
        }

        if(score >= 2) {
            return "Phishing Website";
        }

        return "Safe Website";
    }
}
