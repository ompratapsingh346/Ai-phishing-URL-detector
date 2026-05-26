package com.project.phishingdetector.service;

public class FeatureExtractor {

    public static double[] extractFeatures(String url) {

        double length = url.length();

        double dots = url.chars()
                .filter(ch -> ch == '.')
                .count();

        double hasHttps =
                url.startsWith("https") ? 1 : 0;

        double hyphenCount = url.chars()
                .filter(ch -> ch == '-')
                .count();

        return new double[] {
                length,
                dots,
                hasHttps,
                hyphenCount
        };
    }
}
