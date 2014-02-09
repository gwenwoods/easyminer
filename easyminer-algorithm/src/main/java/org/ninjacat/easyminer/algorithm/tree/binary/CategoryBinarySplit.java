package org.ninjacat.easyminer.algorithm.tree.binary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ninjacat.easyminer.io.FieldData;
import org.ninjacat.easyminer.io.StringData;

public class CategoryBinarySplit {

    String predicateField;

    List<String> leftNodeCategories = new ArrayList<String>();

    Double computeGini(List<FieldData> data) {

        StringData targetData = (StringData) data.get(0);
        StringData predicateFieldData = findPredicateFieldData(data);

        Map<String, Integer> leftChildNoteResults = new HashMap<String, Integer>();
        Map<String, Integer> rightChildNoteResults = new HashMap<String, Integer>();

        int leftRecordCount = 0;
        int rightRecordCount = 0;

        for (int i = 0; i < targetData.getData().size(); i++) {
            String targetRecordCate = targetData.getData().get(i);
            String predicateDataRecord = predicateFieldData.getData().get(i);

            if (leftNodeCategories.contains(predicateDataRecord)) {

                leftRecordCount++;

                Integer count = leftChildNoteResults.get(targetRecordCate);
                if (count == null) {
                    count = 1;
                }

                leftChildNoteResults.put(targetRecordCate, count);

            } else {

                rightRecordCount++;
                Integer count = rightChildNoteResults.get(targetRecordCate);
                if (count == null) {
                    count = 1;
                }

                rightChildNoteResults.put(targetRecordCate, count);
            }
        }

        double leftGini = 1.0;
        double rightGini = 1.0;

        if (leftRecordCount == 0 || rightRecordCount == 0) {
            return null;
        }

        for (Entry<String, Integer> entry : leftChildNoteResults.entrySet()) {
            leftGini = leftGini * entry.getValue().doubleValue() / (leftRecordCount);
        }

        for (Entry<String, Integer> entry : rightChildNoteResults.entrySet()) {
            rightGini = rightGini * entry.getValue().doubleValue() / (rightRecordCount);
        }

        return leftGini * rightGini;
    }

    private StringData findPredicateFieldData(List<FieldData> data) {
        // TODO Auto-generated method stub
        return null;
    }
}
