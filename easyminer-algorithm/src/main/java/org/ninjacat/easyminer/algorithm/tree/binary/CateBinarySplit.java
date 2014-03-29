package org.ninjacat.easyminer.algorithm.tree.binary;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.easyminer.io.StringData;

public class CateBinarySplit {

    private final String splitField;

    int leftCateBinaryValue;

    // boolean[] leftCateArray;

    public CateBinarySplit(String splitField, int leftCateBinaryValue) {
        super();
        this.splitField = splitField;
        this.leftCateBinaryValue = leftCateBinaryValue;
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
    public Double childImpurity(StringData data, StringData target) {

        Set<String> leftCateSet = getLeftCateSet(data.getCateLevels());

        ChildRecordDists childRecordDists = getRecordDistInChildren(leftCateSet, data, target);

        return childRecordDists.getChildImpurity();
    }

    /**
     * No modifier for the purpose of unit tests.
     * 
     * @param levels
     *            all categories of the field
     * @return the Set of categories for left child
     */
    Set<String> getLeftCateSet(List<String> levels) {

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

        return leftCateSet;
    }

    private ChildRecordDists getRecordDistInChildren(Set<String> leftCateSet, StringData data,
        StringData target) {

        Map<String, Integer> leftRecordDist = new HashMap<String, Integer>();
        Map<String, Integer> rightRecordDist = new HashMap<String, Integer>();

        int leftCount = 0;

        int rightCount = 0;

        for (int i = 0; i < data.getData().size(); i++) {
            String record = data.getData().get(i);
            String cate = target.getData().get(i);

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
