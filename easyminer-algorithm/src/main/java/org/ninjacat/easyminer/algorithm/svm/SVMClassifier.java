package org.ninjacat.easyminer.algorithm.svm;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public final class SVMClassifier {

    List<Vector<Double>> supportVectors = new ArrayList<Vector<Double>>();

    Vector<Double> svTargets = new Vector<Double>();

    Double constraint = 100.0;

    Double bias = 0.0;

    Double[] alpha;

    public SVMClassifier(List<Vector<Double>> supportVectors, Vector<Double> svTargets, Double constraint,
        Double bias, Double[] alpha) {
        this.supportVectors = supportVectors;
        this.svTargets = svTargets;
        this.constraint = constraint;
        this.bias = bias;
        this.alpha = alpha;
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
    Double train(Vector<Double> x, Double y) {

        Double f = bias;
        for (int i = 0; i < supportVectors.size(); i++) {
            f += alpha[i] * svTargets.get(i) * kernel(x, supportVectors.get(i));
        }

        // ------------------------------------
        // find the dual alpha for SMO
        Double value = f * y;

        Integer index1 = null;
        for (int i = 0; i < alpha.length; i++) {
            if (satisfyKKT(alpha[i], value)) {
                continue;
            } else {
                index1 = i;
                break;
            }
        }

        // for(int )
        return f;
    }

    Double kernel(Vector<Double> x, Vector<Double> sv) {
        return null;
    }

    // Karush-Kuhn-Tucker condition
    public boolean satisfyKKT(Double alpha, Double value) {

        if (alpha < 0 || alpha > constraint) {
            // Code should never reach here!!!
            return false;
        }

        // Double value = fx(x) * y;
        if (alpha == 0) {
            // fx * y should >= 1
            if (value < 1.0) {
                return false;
            }

        } else if (alpha > 0 && alpha < constraint) {
            // fx * y should == 1

            if (value != 1.0) {
                return false;
            }

        } else if (alpha == constraint) {
            // fx * y should <= 1
            if (value > 1.0) {
                return false;
            }

        }
        return true;
    }
}
