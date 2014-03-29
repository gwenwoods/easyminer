package org.ninjacat.easyminer.algorithm.tree.binary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.easyminer.io.FieldData;
import org.easyminer.io.StringData;

public class CategoryBinarySplit {

    String splitField;

    List<String> leftNodeCategories = new ArrayList<String>();

    public CategoryBinarySplit(String predicateField, List<String> leftNodeCategories) {
        super();
        this.splitField = predicateField;
        this.leftNodeCategories = leftNodeCategories;
    }

    Double computeGini(List<FieldData> data) {

        StringData targetData = (StringData) data.get(0);
        StringData predicateFieldData = findPredicateFieldData(data);

        Map<String, Integer> leftChildNodeResults = new HashMap<String, Integer>();
        Map<String, Integer> rightChildNodeResults = new HashMap<String, Integer>();

        int leftRecordCount = 0;
        int rightRecordCount = 0;

        for (int i = 0; i < targetData.getData().size(); i++) {
            String targetRecordCate = targetData.getData().get(i);
            String predicateDataRecord = predicateFieldData.getData().get(i);

            if (leftNodeCategories.contains(predicateDataRecord)) {

                leftRecordCount++;

                Integer count = leftChildNodeResults.get(targetRecordCate);
                if (count == null) {
                    count = 1;
                } else {
                    count++;
                }

                leftChildNodeResults.put(targetRecordCate, count);

            } else {

                rightRecordCount++;
                Integer count = rightChildNodeResults.get(targetRecordCate);
                if (count == null) {
                    count = 1;
                } else {
                    count++;
                }

                rightChildNodeResults.put(targetRecordCate, count);
            }
        }

        double leftGini = 1.0;
        double rightGini = 1.0;

        if (leftRecordCount == 0 || rightRecordCount == 0) {
            return null;
        }

        for (Entry<String, Integer> entry : leftChildNodeResults.entrySet()) {
            double prob = entry.getValue().doubleValue() / (leftRecordCount);
            // System.out.println(prob);
            leftGini -= prob * prob;
        }
        // System.out.println("left count = " + leftRecordCount);
        // System.out.println("left gini = " + leftGini);

        for (Entry<String, Integer> entry : rightChildNodeResults.entrySet()) {
            double prob = entry.getValue().doubleValue() / (rightRecordCount);
            rightGini -= prob * prob;
        }
        // System.out.println("right count = " + rightRecordCount);
        // System.out.println("right gini = " + rightGini);

        int totalCount = leftRecordCount + rightRecordCount;

        return leftGini * ((double) leftRecordCount / (double) totalCount) + rightGini
            * ((double) rightRecordCount / (double) totalCount);
    }

    private StringData findPredicateFieldData(List<FieldData> data) {

        for (FieldData fieldData : data) {
            if (fieldData.getFieldName().equals(splitField)) {
                return (StringData) fieldData;
            }
        }
        return null;
    }
}
