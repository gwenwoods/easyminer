package org.ninjacat.easyminer.io;

import java.util.ArrayList;
import java.util.List;

public final class StringData extends AbstractFieldData {

    List<String> cateLevels = new ArrayList<String>();

    List<String> data = new ArrayList<String>();

    public StringData(String fieldName) {
        super(fieldName);
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
