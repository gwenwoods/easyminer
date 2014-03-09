package org.ninjacat.easyminer.algorithm.regression;

public final class RegressionHypothesis {

    // theta0 is the intercept
    Double theta0;

    // the coefficients
    Double[] thetas;

    // learning rate
    Double alpha;

    // tolerance
    Double tolerence;

    public RegressionHypothesis(Double theta0, Double[] thetas, Double alpha, Double tolerence) {
        this.theta0 = theta0;
        this.thetas = new Double[thetas.length];
        for (int i = 0; i < thetas.length; i++) {
            this.thetas[i] = thetas[i];
        }
        this.alpha = alpha;
        this.tolerence = tolerence;
    }

    public boolean train(Double[] record, Double y) {

        Double hTheta = compute(record);
        Double delta = y - hTheta;

        System.out.println(" delta " + delta);
        if (Math.abs(delta) < tolerence) {
            return false;
        } else {
            update(delta, record);
            return true;
        }

    }

    private Double compute(Double[] record) {

        Double hTheta = theta0;
        for (int fieldIndex = 0; fieldIndex < record.length; fieldIndex++) {
            hTheta += thetas[fieldIndex] * record[fieldIndex];
        }

        return hTheta;
    }

    private boolean update(Double delta, Double[] record) {
        theta0 += alpha * delta;
        for (int fieldIndex = 0; fieldIndex < thetas.length; fieldIndex++) {
            thetas[fieldIndex] += alpha * delta * record[fieldIndex];
        }

        return true;
    }

}
