package org.ninjacat.easyminer.algorithm.binarytree;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ninjacat.easyminer.algorithm.tree.binary.ChildRecordDists;

public class CateSplit implements Split {

    private final String splitField;

    // private final int leftCateBinaryValue;

    private final Set<String> leftCateSet = new HashSet<String>();

    private final Set<String> rightCateSet = new HashSet<String>();

    public CateSplit(String splitField, int leftCateBinaryValue, List<String> levels) {
        super();
        this.splitField = splitField;
        // this.leftCateBinaryValue = leftCateBinaryValue;

        // --------------------------------------------
        // Find left cate set
        char[] leftCateArray = Integer.toBinaryString(leftCateBinaryValue).toCharArray();

        char[] leftCatesPadded = new char[levels.size()];

        int diff = leftCatesPadded.length - leftCateArray.length;

        for (int i = 0; i < leftCatesPadded.length; i++) {
            if (i < diff) {
                leftCatesPadded[i] = '0';
            } else {
                leftCatesPadded[i] = leftCateArray[i - diff];
            }
        }

        Set<String> leftCateSet = new HashSet<String>();

        for (int i = 0; i < leftCatesPadded.length; i++) {
            if (leftCatesPadded[i] == '1') {
                leftCateSet.add(levels.get(i));
            }
        }

        rightCateSet.addAll(levels);
        rightCateSet.removeAll(leftCateSet);
        // this.leftCateArray = new boolean[array.length];
        // for (int i = 0; i < array.length; i++) {
        // this.leftCateArray[i] = array[i];
        // }
    }

    /**
     * i(t) is the impurity function
     * 
     * delta_i = i(t) - p_r * i(t_r) - p_l * i(p_l)
     * 
     * child_impurity = p_r * i(t_r) - p_l * i(p_l)
     * 
     * Maximize delta_i is the same as minimize child_impurity.
     * 
     * 
     * @return the child_impurity
     */
    public Double childImpurity(String[] data, String[] target, List<String> levels, boolean[] nodeRecords) {

        // Set<String> leftCateSet = getLeftCateSet(levels);

        ChildRecordDists childRecordDists = getRecordDistInChildren(leftCateSet, data, target, nodeRecords);

        return childRecordDists.getChildImpurity();
    }

    public Set<String> getLeftCateSet() {
        return leftCateSet;
    }

    public Set<String> getRightCateSet() {
        return rightCateSet;
    }

    private ChildRecordDists getRecordDistInChildren(Set<String> leftCateSet, String[] data, String[] target,
        boolean[] nodeRecords) {

        Map<String, Integer> leftRecordDist = new HashMap<String, Integer>();
        Map<String, Integer> rightRecordDist = new HashMap<String, Integer>();

        int leftCount = 0;

        int rightCount = 0;

        for (int i = 0; i < data.length; i++) {
            if (!nodeRecords[i]) {
                continue;
            }

            String record = data[i];
            String cate = target[i];

            if (leftCateSet.contains(record)) {

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
}
