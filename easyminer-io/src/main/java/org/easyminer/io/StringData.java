package org.easyminer.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class StringData extends AbstractFieldData {

    List<String> cateLevels = new ArrayList<String>();

    List<String> data = new ArrayList<String>();

    String winningCate = null;

    public StringData(String fieldName) {
        super(fieldName);
    }

    public List<String> getData() {
        return data;
    }

    public List<String> getCateLevels() {
        return cateLevels;
    }

    public String getWinningCate() {

        if (winningCate != null) {
            return winningCate;
        }

        Map<String, Integer> recordsDistMap = new HashMap<String, Integer>();

        for (String record : data) {
            Integer recordCount = recordsDistMap.get(record);
            if (recordCount != null) {
                recordsDistMap.put(record, recordCount + 1);
            } else {
                recordsDistMap.put(record, 1);
            }
        }

        // TODO: handle maxCount = 0 special case
        Integer maxCount = 0;

        for (Entry<String, Integer> entry : recordsDistMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                winningCate = entry.getKey();
            }
        }
        // System.out.println(recordsDistMap.toString() + " " + winningCate);
        return winningCate;
    }

    @Override
    public int getRecordCount() {
        return data.size();
    }

    @Override
    public Object getRecord(int i) {
        return data.get(i);
    }

    @Override
    public void addRecord(Object value) {

        String record = (String) value;
        data.add(record);
        if (!cateLevels.contains(record)) {
            cateLevels.add(record);
        }
    }

}
