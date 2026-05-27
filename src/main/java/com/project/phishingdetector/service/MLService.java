package com.project.phishingdetector.service;

import weka.classifiers.Classifier;
import weka.classifiers.trees.RandomForest;
import weka.core.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MLService {

    private static Classifier model;

    static {

        try {

            ArrayList<Attribute> attributes = new ArrayList<>();

            attributes.add(new Attribute("urlLength"));
            attributes.add(new Attribute("hasHttps"));
            attributes.add(new Attribute("hasAt"));

            ArrayList<String> classValues = new ArrayList<>();
            classValues.add("Safe");
            classValues.add("Phishing");

            attributes.add(new Attribute("class", classValues));

            Instances trainingData =
                    new Instances(
                            "PhishingDataset",
                            attributes,
                            0
                    );

            trainingData.setClassIndex(
                    trainingData.numAttributes() - 1
            );

            DenseInstance inst1 = new DenseInstance(4);

            inst1.setValue(attributes.get(0), 10);
            inst1.setValue(attributes.get(1), 1);
            inst1.setValue(attributes.get(2), 0);
            inst1.setValue(attributes.get(3), "Safe");

            DenseInstance inst2 = new DenseInstance(4);

            inst2.setValue(attributes.get(0), 80);
            inst2.setValue(attributes.get(1), 0);
            inst2.setValue(attributes.get(2), 1);
            inst2.setValue(attributes.get(3), "Phishing");

            trainingData.add(inst1);
            trainingData.add(inst2);

            model = new RandomForest();

            model.buildClassifier(trainingData);

            System.out.println("ML Model Trained Successfully");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static Map<String, Object> predict(String url) {

        Map<String, Object> response = new HashMap<>();

        try {

            int riskScore = 0;

            List<String> reasons = new ArrayList<>();

            // URL Length

            if (url.length() > 25) {

                riskScore += 25;

                reasons.add("URL is too long");
            }

            // HTTPS Check

            if (!url.startsWith("https")) {

                riskScore += 20;

                reasons.add("No HTTPS detected");
            }

            // @ Symbol Check

            if (url.contains("@")) {

                riskScore += 20;

                reasons.add("Contains @ symbol");
            }

            // Suspicious Keywords

            if (url.contains("login")
                    || url.contains("bank")
                    || url.contains("verify")
                    || url.contains("secure")) {

                riskScore += 25;

                reasons.add("Contains suspicious keywords");
            }

            // Special Characters

            if (url.contains("-")) {

                riskScore += 10;

                reasons.add("Contains special characters");
            }

            // Final Prediction

            String result;

            if (riskScore >= 50) {

                result = "Phishing";

            } else {

                result = "Safe";
            }

            response.put("url", url);

            response.put("result", result);

            response.put("riskScore", riskScore + "%");

            response.put("reasons", reasons);

            return response;

        } catch (Exception e) {

            e.printStackTrace();

            response.put("result", "Error");

            response.put("url", url);

            return response;
        }
    }
}