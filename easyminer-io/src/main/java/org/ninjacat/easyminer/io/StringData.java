package org.ninjacat.easyminer.io;

import java.util.ArrayList;
import java.util.List;

public final class StringData implements FieldData {

    String fieldName;

    List<String> cateLevels = new ArrayList<String>();

    List<String> data = new ArrayList<String>();

    boolean isTarget = false;

    public StringData(String fieldName) {
        super();
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public List<String> getData() {
        return data;
    }

    public void addRecord(String record) {

        data.add(record);
        if (!cateLevels.contains(record)) {
            cateLevels.add(record);
        }
    }
}
