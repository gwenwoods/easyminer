package org.ninjacat.easyminer.algorithm.regression;

public class RegressionLearner {

    Double alpha = 0.001;

    Double tolerence = 0.01;

    Integer iterations = 100;

    Boolean isBatch = true;

    public RegressionModel fitRegression(Integer recordNum, Integer fieldNum, Double[][] data, Double[] y) {

        Double[] thetas = new Double[fieldNum];

        Double theta0 = 1.0;
        // --------------------------------
        // Initialize thetas
        for (int i = 0; i < fieldNum; i++) {
            thetas[i] = 1.0;
        }

        // ---------------------------------
        // Cost Function:
        // J(theta) = 0.5 * { sum (recordIndex) (y_i - h_theta)^2}
        // h_theta is functions/hypotheses
        // theta_j = theta_j - alpha * dJ/d_theta_j

        isBatch = false;
        if (isBatch) {
            for (int iter = 0; iter < iterations; iter++) {
                System.out.println(" iter " + iter);
                Double delta0 = 0.0;
                Double[] deltaj = new Double[fieldNum];
                for (int fieldIndex = 0; fieldIndex < fieldNum; fieldIndex++) {

                    deltaj[fieldIndex] = 0.0;
                }
                for (int recordIndex = 0; recordIndex < recordNum; recordIndex++) {
                    Double hTheta = theta0;
                    for (int fieldIndex = 0; fieldIndex < fieldNum; fieldIndex++) {
                        hTheta += thetas[fieldIndex] * data[recordIndex][fieldIndex];
                        deltaj[fieldIndex] += (y[recordIndex] - hTheta) * data[recordIndex][fieldIndex];
                    }

                    // -----------------------------------
                    // dJ/d_theta_j = (h_theta - y)*x_j
                    // => theta_j = theta_j + alpha* (y_i - h_theta) * xj

                    delta0 += y[recordIndex] - hTheta;

                    // System.out.println(" delta " + delta);
                    // if (Math.abs(delta) < tolerence) {
                    // break;
                    // }

                }

                theta0 += alpha * delta0;
                for (int fieldIndex = 0; fieldIndex < fieldNum; fieldIndex++) {
                    thetas[fieldIndex] += alpha * deltaj[fieldIndex];
                }
            }
        } else {
            for (int iter = 0; iter < iterations; iter++) {
                System.out.println(" iter " + iter);
                for (int recordIndex = 0; recordIndex < recordNum; recordIndex++) {
                    Double hTheta = theta0;
                    for (int fieldIndex = 0; fieldIndex < fieldNum; fieldIndex++) {
                        hTheta += thetas[fieldIndex] * data[recordIndex][fieldIndex];
                    }

                    // -----------------------------------
                    // dJ/d_theta_j = (h_theta - y)*x_j
                    // => theta_j = theta_j + alpha* (y_i - h_theta) * xj

                    Double delta = y[recordIndex] - hTheta;

                    System.out.println(" delta " + delta);
                    if (Math.abs(delta) < tolerence) {
                        break;
                    }
                    theta0 += alpha * delta;
                    for (int fieldIndex = 0; fieldIndex < fieldNum; fieldIndex++) {
                        thetas[fieldIndex] +=
                            alpha * (y[recordIndex] - hTheta) * data[recordIndex][fieldIndex];
                    }
                }
            }
        }

        for (Double t : thetas) {
            System.out.println(t);
        }
        System.out.println(theta0);
        return null;
    }
}
