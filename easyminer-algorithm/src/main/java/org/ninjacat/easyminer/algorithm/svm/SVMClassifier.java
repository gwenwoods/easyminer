package org.ninjacat.easyminer.algorithm.svm;

import java.util.List;
import java.util.Vector;

public final class SVMClassifier {

    SVMObjectiveFunction objFun;
    // List<Vector<Double>> supportVectors = new ArrayList<Vector<Double>>();

    // Vector<Double> svTargets = new Vector<Double>();

    Double constraint = 100.0;

    // Double bias = 0.0;

    // Double[] alpha;

    Double[] error;

    public SVMClassifier(List<Vector<Double>> supportVectors, Vector<Double> svTargets, Double constraint,
        Double bias, Double[] alpha) {

        this.objFun = new SVMObjectiveFunction(supportVectors, svTargets, SVMKernel.LINEAR);
        // this.supportVectors = supportVectors;
        // this.svTargets = svTargets;
        this.constraint = constraint;
        // this.bias = bias;
        // this.alpha = alpha;
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
    Double train() {

        // -----------------------------------
        // find first alpha index alpha_i1

        Integer alpha_i1 = null;
        Double[] alphaArray = objFun.alpha;

        for (int i = 0; i < alphaArray.length; i++) {
            Double objFunValue = objFun.computeTraining(i);
            Double value = objFunValue * objFun.svTargets.get(i);
            if (satisfyKKT(alphaArray[i], value)) {
                continue;
            } else {
                alpha_i1 = i;
                break;
            }
        }

        if (alpha_i1 == null) {
            return null;
        }

        // find second alpha index alpha_i2
        Double e1 = objFun.error[alpha_i1];

        Double maxDeltaE = Math.abs(e1 - objFun.error[0]);
        Integer alpha_i2 = 0;
        for (int i = 1; i < objFun.error.length; i++) {
            Double deltaE = Math.abs(e1 - objFun.error[i]);
            if (deltaE > maxDeltaE) {
                maxDeltaE = deltaE;
                alpha_i2 = i;
            }
        }

        // ------------------
        // now we have alpha_i1 and alpha_i2

        objFun.updateDualAlpha(alpha_i1, alpha_i2);
        // SequentialMinimalOptimizer.updateDualAlpha(alpha1, alpha2, x1, x2, y1, y2)

        return null;
    }

    // Double kernel(Vector<Double> x, Vector<Double> sv) {
    // return null;
    // }

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
