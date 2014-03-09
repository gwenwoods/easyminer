package org.ninjacat.easyminer.algorithm.svm;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public final class SVMLearner {

    List<RecordData> supportVectors = new ArrayList<RecordData>();

    Double[] y;

    Double costParameter = 100.0;

    Double bias = 0.0;

    Double[] alpha;

    public SupportVectorMachine fit(Integer recordNum, Integer fieldNum, Double[][] data, Double[] y) {

        alpha = new Double[y.length];

        for (int i = 0; i < recordNum; i++) {
            Double Ei = fx(supportVectors.get(i).data) - y[i];
        }

        return null;
    }

    /**
     * Classifier function.
     * 
     * f(x) = w^T x + b
     * 
     * f(x) = sum(i : all training points) [alpha_i * y_i * kernel(x,xi) ]
     * 
     * @param x
     * @return
     */
    Double fx(Vector<Double> x) {

        Double f = bias;
        for (int i = 0; i < supportVectors.size(); i++) {
            f += alpha[i] * y[i] * kernel(x, supportVectors.get(i).data);
        }
        return f;
    }

    Double kernel(Vector<Double> x, Vector<Double> sv) {
        return null;
    }
}
