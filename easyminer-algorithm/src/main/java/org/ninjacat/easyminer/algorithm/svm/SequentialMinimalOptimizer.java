package org.ninjacat.easyminer.algorithm.svm;

public class SequentialMinimalOptimizer {

    Double constraint = 100.0;

    public Double[] updateDualAlpha(Double alpha1, Double alpha2, Double[] x1, Double[] x2, Double y1,
        Double y2) {

        Double[] alpha = new Double[2];

        Double lAlpha2 = findL(y1, y2, alpha1, alpha2);
        Double hAlpha2 = findH(y1, y2, alpha1, alpha2);

        Double e1 = computeE(x1, y1);
        Double e2 = computeE(x2, y2);

        Double eta = computeEta(x1, x2);
        // TODO: take care of the case of eta=0

        // ----------------------------------------
        // alpha1 is alpha[0]
        // alpha2 is alpha[1]
        alpha[1] = alpha2 - y2 * (e1 - e2) / eta;

        // clip alpha[1]
        if (alpha[1] >= hAlpha2) {
            alpha[1] = hAlpha2;
        } else if (alpha[1] < lAlpha2) {
            alpha[1] = lAlpha2;
        }

        alpha[0] = alpha1 + y1 * y2 * (alpha2 = alpha[1]);

        return alpha;
    }

    private Double computeEta(Double[] x1, Double[] x2) {
        // TODO Auto-generated method stub
        return null;
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

    private Double computeE(Double[] x, Double y) {
        // TODO:
        // compute error here : Ei= f_old(xi) -yi
        return null;
    }

}
