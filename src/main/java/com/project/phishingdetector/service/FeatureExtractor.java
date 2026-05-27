package com.project.phishingdetector.service;

public class FeatureExtractor {

    public static double[] extract(String url) {

        double[] features = new double[31];

        // 1. having_IP_Address
        features[0] = url.matches(".*\\d+.*") ? -1 : 1;

        // 2. URL_Length
        features[1] = url.length() > 25 ? -1 : 1;

        // 3. Shortining_Service
        features[2] =
                (url.contains("bit.ly") ||
                 url.contains("tinyurl"))
                        ? -1 : 1;

        // 4. having_At_Symbol
        features[3] = url.contains("@") ? -1 : 1;

        // 5. double_slash_redirecting
        features[4] = url.contains("//") ? -1 : 1;

        // 6. Prefix_Suffix
        features[5] = url.contains("-") ? -1 : 1;

        // Remaining features
        for(int i = 6; i < 30; i++) {

            features[i] = 1;
        }

        // Last column = class label
        features[30] = 1;

        return features;
    }
}