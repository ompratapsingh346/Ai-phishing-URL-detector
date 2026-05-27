package com.project.phishingdetector.service;

import weka.classifiers.Classifier;
import weka.classifiers.trees.RandomForest;
import weka.core.*;

import java.util.ArrayList;

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

            Instances trainingData = new Instances(
                    "PhishingDataset",
                    attributes,
                    0
            );

            trainingData.setClassIndex(trainingData.numAttributes() - 1);

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

    public static String predict(String url) {

        try {

            ArrayList<Attribute> attributes = new ArrayList<>();

            attributes.add(new Attribute("urlLength"));
            attributes.add(new Attribute("hasHttps"));
            attributes.add(new Attribute("hasAt"));

            ArrayList<String> classValues = new ArrayList<>();
            classValues.add("Safe");
            classValues.add("Phishing");

            attributes.add(new Attribute("class", classValues));

            Instances testData = new Instances(
                    "TestDataset",
                    attributes,
                    0
            );

            testData.setClassIndex(testData.numAttributes() - 1);

            DenseInstance instance = new DenseInstance(4);

            instance.setValue(attributes.get(0), url.length());
            instance.setValue(attributes.get(1),
                    url.startsWith("https") ? 1 : 0);

            instance.setValue(attributes.get(2),
                    url.contains("@") ? 1 : 0);

            instance.setDataset(testData);

            double result = model.classifyInstance(instance);

            return classValues.get((int) result);

        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
}