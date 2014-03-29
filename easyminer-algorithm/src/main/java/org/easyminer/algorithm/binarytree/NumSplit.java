package org.easyminer.algorithm.binarytree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ninjacat.easyminer.algorithm.tree.binary.ChildRecordDists;

public class NumSplit implements Split {

    private final String splitField;

    // ---------------------------------
    // Left <= splitValue
    // Right > splitValue
    private final Double splitValue;

    public NumSplit(String splitField, Double splitValue) {
        this.splitField = splitField;
        this.splitValue = splitValue;

    }

    /**
     * i(t) is the impurity function
     * 
     * delta_i = i(t) - p_r * i(t_r) - p_l * i(p_l)
     * 
     * child_impurity = p_r * i(t_r) + p_l * i(p_l)
     * 
     * Maximize delta_i is the same as minimize child_impurity.
     * 
     * 
     * @return the child_impurity
     */
    public Double childImpurity(Double[] data, String[] target, List<String> levels, boolean[] nodeRecords) {

        ChildRecordDists childRecordDists = getRecordDistInChildren(data, target, nodeRecords);

        return childRecordDists.getChildImpurity();
    }

    private ChildRecordDists getRecordDistInChildren(Double[] data, String[] target, boolean[] nodeRecords) {

        Map<String, Integer> leftRecordDist = new HashMap<String, Integer>();
        Map<String, Integer> rightRecordDist = new HashMap<String, Integer>();

        int leftCount = 0;
        int rightCount = 0;

        for (int i = 0; i < data.length; i++) {
            if (!nodeRecords[i]) {
                continue;
            }

            Double record = data[i];
            String cate = target[i];

            if (record <= splitValue) {

                leftCount++;
                Integer count = leftRecordDist.get(cate);
                if (count == null) {
                    leftRecordDist.put(cate, 1);
                } else {
                    count++;
                    leftRecordDist.put(cate, count);
                }
            } else {

                rightCount++;
                Integer count = rightRecordDist.get(cate);
                if (count == null) {
                    rightRecordDist.put(cate, 1);
                } else {
                    count++;
                    rightRecordDist.put(cate, count);
                }
            }
        }

        return new ChildRecordDists(leftRecordDist, rightRecordDist, leftCount, rightCount);
    }

    public String getSplitField() {
        return splitField;
    }

    public Double getSplitValue() {
        return splitValue;
    }
}
