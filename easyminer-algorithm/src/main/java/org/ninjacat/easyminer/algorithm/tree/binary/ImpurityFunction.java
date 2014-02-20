package org.ninjacat.easyminer.algorithm.tree.binary;

public class ImpurityFunction {

    public static final Double evaluate(Double... probs) {

        int n = probs.length;

        Double impurity = 0.0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                impurity += probs[i] * probs[j];
            }
        }
        return impurity;
    }
}
