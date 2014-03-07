package org.ninjacat.easyminer.algorithm.regression;

import java.util.ArrayList;
import java.util.List;

public class RegressionModel {

    private final Double intercept;
    private final List<Double> thetas = new ArrayList<Double>();
    private final List<Double> deltaj = new ArrayList<Double>();

    private final RegressionLearningPara para;

    private RegressionModel(Double intercept, List<Double> thetas, List<Double> deltaj,
        RegressionLearningPara para) {
        this.intercept = intercept;
        this.thetas.addAll(thetas);
        this.deltaj.addAll(deltaj);
        this.para = para;
    }

    public Double[] predict(Double[][] data, boolean updateModel) {

        int recordNum = data.length;
        int fieldNum = data[0].length;
        for (int i = 0; i < para.iterations; i++) {

            System.out.println(" iter " + i);
            for (int recordIndex = 0; recordIndex < recordNum; recordIndex++) {
                Double hTheta = this.intercept;
                for (int fieldIndex = 0; fieldIndex < fieldNum; fieldIndex++) {
                    hTheta += thetas.get(fieldIndex) * data[recordIndex][fieldIndex];
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
                    thetas[fieldIndex] += alpha * (y[recordIndex] - hTheta) * data[recordIndex][fieldIndex];
                }
            }
        }

        return null;
    }

}
