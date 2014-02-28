package org.ninjacat.easyminer.algorithm.svm;

import java.util.ArrayList;
import java.util.List;

public class SVMLearner {

    List<RecordData> supportVectors = new ArrayList<RecordData>();

    Double[] y;

    Double costParameter = 100.0;

    Double bias = 0.0;

    Double[] alpha;

    public SupportVectorMachine fit(Integer recordNum, Integer fieldNum, Double[][] data, Double[] y) {

        alpha = new Double[y.length];

        for (int i = 0; i < y.length; i++) {
            for (int j = 0; j < y.length; j++) {

            }
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
    Double fx(Double[] x) {

        Double f = bias;
        for (int i = 0; i < supportVectors.size(); i++) {
            f += alpha[i] * y[i] * kernel(x, supportVectors.get(i));
        }
        return f;
    }

    Double kernel(Double[] x, RecordData sv) {
        return null;
    }
}
