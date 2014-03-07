package org.ninjacat.easyminer.algorithm.regression;

public class RegressionLearningPara {

    Double alpha = 0.001;

    Double tolerence = 0.01;

    Integer iterations = 100;

    Boolean isBatch = true;

    RegressionLearningPara(Double alpha, Double tolerence, Integer iterations, Boolean isBatch) {
        this.alpha = alpha;
        this.tolerence = tolerence;
        this.iterations = iterations;
        this.isBatch = isBatch;
    }

}
