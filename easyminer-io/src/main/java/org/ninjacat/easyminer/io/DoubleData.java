package org.ninjacat.easyminer.io;

import java.util.ArrayList;
import java.util.List;

public final class DoubleData implements FieldData {

    String fieldName;

    List<Double> data = new ArrayList<Double>();

    Double min = null;

    Double max = null;

    public DoubleData(String fieldName) {
        super();
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public List<Double> getData() {
        return data;
    }

    public Double getMin() {
        if (min == null)
            evaluateMin();

        return min;
    }

    public Double getMax() {
        if (max == null)
            evaluateMax();

        return max;
    }

    public void addRecord(Double record) {
        data.add(record);
    }

    private void evaluateMin() {
        min = data.get(0);
        for (Double record : data) {
            if (record < min) {
                min = record;
            }
        }
    }

    private void evaluateMax() {
        max = data.get(0);
        for (Double record : data) {
            if (record > max) {
                max = record;
            }
        }
    }
}
