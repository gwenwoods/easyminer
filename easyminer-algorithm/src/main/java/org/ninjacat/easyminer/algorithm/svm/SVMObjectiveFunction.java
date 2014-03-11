package org.ninjacat.easyminer.algorithm.svm;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public final class SVMObjectiveFunction {

    List<Vector<Double>> supportVectors = new ArrayList<Vector<Double>>();

    Vector<Double> svTargets = new Vector<Double>();

    Double bias = 0.0;

    Double[] alpha;

    Double[] error;

    public SVMObjectiveFunction(List<Vector<Double>> supportVectors, Vector<Double> svTargets) {
        this.supportVectors = supportVectors;
        this.svTargets = svTargets;

        initialization(supportVectors.size());
    }

    private void initialization(int numSV) {

        bias = 1.0;
        for (int i = 0; i < numSV; i++) {
            alpha[i] = 1.0;
        }

        Double f;
        for (int i = 0; i < numSV; i++) {
            // This loop is to going over all training data points
            f = bias;
            Vector<Double> trainingX = supportVectors.get(i);
            for (int j = 0; j < numSV; j++) {
                f += alpha[j] * svTargets.get(j) * kernel(trainingX, supportVectors.get(j));
            }
            error[i] = f - svTargets.get(i);
        }
    }

    Double kernel(Vector<Double> x, Vector<Double> sv) {
        return null;
    }
}
