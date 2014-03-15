package org.ninjacat.easyminer.algorithm.svm;

import java.util.List;
import java.util.Vector;

public final class SVMClassifier {

    SVMKernel kernel;

    // Both KKT condition and SMO need constraint
    Double constraint = 100.0;

    Integer maxIter = 5000;

    public SVMClassifier(SVMKernel kernel, Double constraint, Integer maxIter) {
        this.kernel = kernel;
        this.constraint = constraint;
        this.maxIter = maxIter;
    }

    /**
     * Classifier function.
     * 
     * f(x) = w^T x + b
     * 
     * f(x) = sum(i : all training points) [alpha_i * y_i * kernel(x,xi) ]
     * 
     * @param x
     * @return true for succeed finishing training
     */

    SupportVectorMachine train(List<Vector<Double>> supportVectors, Vector<Double> svTargets) {

        SVMObjectiveFunction objFun = new SVMObjectiveFunction(supportVectors, svTargets, kernel, constraint);

        boolean doneTraining = false;

        int iterCount = 0;

        while (!doneTraining && iterCount < maxIter) {

            iterCount++;
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
                doneTraining = true;
                return objFun.exportSVM();
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

        }
        return objFun.exportSVM();
    }

    //
    /**
     * Karush-Kuhn-Tucker condition
     * 
     * @param alpha
     *            the alpha to be checked
     * @param value
     *            fx * y
     */
    public boolean satisfyKKT(Double alpha, Double value) {

        if (alpha < 0 || alpha > constraint) {
            // Code should never reach here!!!
            return false;
        }

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
