package org.ninjacat.easyminer.algorithm.svm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public final class SVMLearner {

    List<RecordData> supportVectors = new ArrayList<RecordData>();

    Double[] y;

    Double constraint = 100.0;

    Double bias = 0.0;

    Double[] alpha;

    public SupportVectorMachine fit(Integer recordNum, Integer fieldNum, Double[][] data, Double[] y) {

        SVMClassifier fx = new SVMClassifier(null, null, bias, bias, y);

        alpha = new Double[y.length];

        for (int i = 0; i < recordNum; i++) {
            // Double Ei = fx.train(supportVectors.get(i).data) - y[i];
            fx.train(new Vector<Double>(Arrays.asList(data[i])), y[i]);
        }

        return null;
    }

    // /**
    // * Classifier function.
    // *
    // * f(x) = w^T x + b
    // *
    // * f(x) = sum(i : all training points) [alpha_i * y_i * kernel(x,xi) ]
    // *
    // * @param x
    // * @return
    // */
    // Double fx(Vector<Double> x) {
    //
    // Double f = bias;
    // for (int i = 0; i < supportVectors.size(); i++) {
    // f += alpha[i] * y[i] * kernel(x, supportVectors.get(i).data);
    // }
    // return f;
    // }

    // Double kernel(Vector<Double> x, Vector<Double> sv) {
    // return null;
    // }
    //
    // // Karush-Kuhn-Tucker condition
    // public boolean satisfyKKT(Double alpha, Vector<Double> x, Double y) {
    //
    // if (alpha < 0 || alpha > constraint) {
    // // Code should never reach here!!!
    // return false;
    // }
    //
    // Double value = fx(x) * y;
    // if (alpha == 0) {
    // // fx * y should >= 1
    // if (value < 1.0) {
    // return false;
    // }
    //
    // } else if (alpha > 0 && alpha < constraint) {
    // // fx * y should == 1
    //
    // if (value != 1.0) {
    // return false;
    // }
    //
    // } else if (alpha == constraint) {
    // // fx * y should <= 1
    // if (value > 1.0) {
    // return false;
    // }
    //
    // }
    // return true;
    // }

}
