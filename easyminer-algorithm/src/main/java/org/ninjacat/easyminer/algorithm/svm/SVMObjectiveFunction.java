package org.ninjacat.easyminer.algorithm.svm;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public final class SVMObjectiveFunction {

    Double constraint = 100.0;

    List<Vector<Double>> supportVectors = new ArrayList<Vector<Double>>();

    Vector<Double> svTargets = new Vector<Double>();

    Double bias = 0.0;

    Double[] alpha;

    Double[] error;

    SVMKernel kernel;

    public SVMObjectiveFunction(List<Vector<Double>> supportVectors, Vector<Double> svTargets,
        SVMKernel kernel, Double constraint) {
        this.supportVectors.addAll(supportVectors);
        this.svTargets.addAll(svTargets);

        this.kernel = kernel != null ? kernel : SVMKernel.LINEAR;
        this.constraint = constraint;

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
                f += alpha[j] * svTargets.get(j) * kernelFun(trainingX, supportVectors.get(j));
            }
            error[i] = f - svTargets.get(i);
        }
    }

    Double kernelFun(Vector<Double> x, Vector<Double> sv) {

        Double value = 0.0;
        if (SVMKernel.LINEAR == kernel) {
            for (int i = 0; i < x.size(); i++) {
                if (alpha[i] == 0) {
                    // This if might not needed as in compute function already checked
                    // TODO: or alpha_i < epsilon
                    continue;
                }

                value += x.get(i) * sv.get(i);
            }
        }
        return value;
    }

    Double computeTraining(int trainingDataIndex) {

        Vector<Double> data = supportVectors.get(trainingDataIndex);
        Double value = bias;
        for (int i = 0; i < supportVectors.size(); i++) {
            if (alpha[i] == 0) {
                // TODO: or alpha_i < epsilon
                continue;
            }

            value += alpha[i] * kernelFun(data, supportVectors.get(i));
        }

        error[trainingDataIndex] = value - svTargets.get(trainingDataIndex);
        return value;
    }

    /**
     * SequentialMinimalOptimizer
     * 
     * @param alpha_i1
     *            the first index for the joint optimization
     * @param alpha_i2
     *            the second index for the joint optimization
     */
    void updateDualAlpha(int alpha_i1, int alpha_i2) {

        Double[] dualAlpha_new = new Double[2];

        Vector<Double> x1 = supportVectors.get(alpha_i1);
        Vector<Double> x2 = supportVectors.get(alpha_i2);

        Double y1 = svTargets.get(alpha_i1);
        Double y2 = svTargets.get(alpha_i2);

        Double alpha1_old = alpha[alpha_i1];
        Double alpha2_old = alpha[alpha_i2];

        Double e1 = error[alpha_i1];
        Double e2 = error[alpha_i2];

        Double lAlpha2 = findL(y1, y2, alpha1_old, alpha2_old);
        Double hAlpha2 = findH(y1, y2, alpha1_old, alpha2_old);

        Double eta = computeEta(x1, x2);
        // TODO: take care of the case of eta=0

        // ----------------------------------------
        // alpha1 is dualAlpha[0]
        // alpha2 is dualAlpha[1]
        dualAlpha_new[1] = alpha2_old - y2 * (e1 - e2) / eta;

        // clip alpha[1]
        if (dualAlpha_new[1] >= hAlpha2) {
            dualAlpha_new[1] = hAlpha2;
        } else if (dualAlpha_new[1] < lAlpha2) {
            dualAlpha_new[1] = lAlpha2;
        }

        dualAlpha_new[0] = alpha1_old + y1 * y2 * (alpha2_old = dualAlpha_new[1]);

        alpha[alpha_i1] = dualAlpha_new[0];
        alpha[alpha_i2] = dualAlpha_new[1];
    }

    private Double computeEta(Vector<Double> x1, Vector<Double> x2) {
        return 2 * kernelFun(x1, x2) - kernelFun(x1, x1) - kernelFun(x2, x2);
    }

    private Double findL(Double y1, Double y2, Double alpha1, Double alpha2) {
        if (y1 != y2) {
            return Math.max(0, alpha2 - alpha1);
        } else {
            return Math.max(0, alpha2 + alpha1 - constraint);
        }
    }

    private Double findH(Double y1, Double y2, Double alpha1, Double alpha2) {
        if (y1 != y2) {
            return Math.min(constraint, constraint + alpha2 - alpha1);
        } else {
            return Math.min(constraint, alpha1 + alpha2);
        }
    }

    public SupportVectorMachine exportSVM() {
        return null;
    }
}
