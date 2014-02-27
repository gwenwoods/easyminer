package org.ninjacat.easyminer.algorithm.regression;

import java.util.ArrayList;
import java.util.List;

public class RegressionModel {

    private final Double intercept;
    private final List<Double> thetas = new ArrayList<Double>();

    private RegressionModel(Double intercept, List<Double> thetas) {
        super();
        this.intercept = intercept;
        this.thetas.addAll(thetas);
    }

}
