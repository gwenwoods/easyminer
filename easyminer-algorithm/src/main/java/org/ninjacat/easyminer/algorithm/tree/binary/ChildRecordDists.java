package org.ninjacat.easyminer.algorithm.tree.binary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ChildRecordDists {

    Map<String, Integer> leftRecordDist = new HashMap<String, Integer>();
    Map<String, Integer> rightRecordDist = new HashMap<String, Integer>();
    Integer leftCount;
    Integer rightCount;

    public ChildRecordDists(Map<String, Integer> leftRecordDist, Map<String, Integer> rightRecordDist,
        Integer leftCount, Integer rightCount) {
        super();
        this.leftRecordDist = leftRecordDist;
        this.rightRecordDist = rightRecordDist;
        this.leftCount = leftCount;
        this.rightCount = rightCount;
    }

    public Double getChildImpurity() {

        List<Double> leftChildProbs = new ArrayList<Double>();
        for (Entry<String, Integer> entry : leftRecordDist.entrySet()) {
            Double prob = entry.getValue().doubleValue() / leftCount.doubleValue();
            leftChildProbs.add(prob);
        }

        Double leftChildImpurity =
            ImpurityFunction.evaluate(leftChildProbs.toArray(new Double[leftChildProbs.size()]));

        List<Double> rightChildProbs = new ArrayList<Double>();
        for (Entry<String, Integer> entry : rightRecordDist.entrySet()) {
            Double prob = entry.getValue().doubleValue() / rightCount.doubleValue();
            rightChildProbs.add(prob);
        }

        Double rightChildImpurity =
            ImpurityFunction.evaluate(rightChildProbs.toArray(new Double[rightChildProbs.size()]));

        Double totalCount = leftCount.doubleValue() + rightCount.doubleValue();

        Double impurity =
            (leftChildImpurity * leftCount.doubleValue() + rightChildImpurity * rightCount.doubleValue())
                / totalCount;

        return impurity;
    }
}
