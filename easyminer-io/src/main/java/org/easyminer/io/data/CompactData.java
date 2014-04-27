package org.easyminer.io.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The data.
 */
public final class CompactData {

    // String[][] data;

    int rowNum;
    String[] header;

    String[] columnType;

    String[][] cateData;
    Double[][] numericData;

    String[] cateHeader;
    String[] numericHeader;

    CateFieldStat[] cateFieldStat;

    int cateFieldNum;
    int numericFieldNum;

    // String baseFilename;
    // List<String> headFields = new ArrayList<String>();

    public CompactData(String[][] data, String[] header) {

        rowNum = data.length;
        int columnNum = data[0].length;

        columnType = new String[columnNum];
        numericFieldNum = columnNum;

        for (int j = 0; j < columnNum; j++) {
            columnType[j] = "Double";

            for (int i = 0; i < rowNum; i++) {

                if (data[i][j] != null) {
                    try {
                        Double.parseDouble(data[i][j]);
                    } catch (NumberFormatException e) {
                        columnType[j] = "String";
                        cateFieldNum++;
                        numericFieldNum--;
                        break;
                    }
                }
            }
        }

        cateData = new String[rowNum][cateFieldNum];
        numericData = new Double[rowNum][numericFieldNum];

        cateHeader = new String[cateFieldNum];
        numericHeader = new String[numericFieldNum];

        cateFieldStat = new CateFieldStat[cateFieldNum];
        int cateIndex = 0;
        int numericIndex = 0;

        // System.out.println("meow " + cateData[1][1]);

        for (int j = 0; j < columnNum; j++) {

            if ("Double".equals(columnType[j])) {
                for (int i = 0; i < rowNum; i++) {

                    if (data[i][j] != null) {
                        try {
                            Double value = Double.parseDouble(data[i][j]);
                            numericData[i][numericIndex] = value;
                            numericHeader[numericIndex] = header[j];
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            // should not happen here
                        }
                    }
                }
                numericIndex++;
            } else {

                String[] fieldData = new String[rowNum];
                for (int i = 0; i < rowNum; i++) {

                    if (data[i][j] != null) {
                        cateData[i][cateIndex] = data[i][j];
                        cateHeader[cateIndex] = header[j];
                        fieldData[i] = data[i][j];
                    }
                }
                cateFieldStat[cateIndex] = findCateFieldStat(header[j], fieldData);
                cateIndex++;
            }

        }

    }

    public String[] getCateHeader() {
        return cateHeader;
    }

    public String[] getNumericHeader() {
        return numericHeader;
    }

    public String[][] getCateData() {
        return cateData;
    }

    public Double[][] getNumericData() {
        return numericData;
    }

    // public void addHeaderField(String fieldName) {
    // headFields.add(fieldName);
    // }
    //
    // public List<String> getHeaderFields() {
    // return Collections.unmodifiableList(headFields);
    // }

    public String[] getColumnType() {
        return columnType;
    }

    public CateFieldStat getCateFieldStat(String fieldName) {

        Integer index = null;

        for (int i = 0; i < cateHeader.length; i++) {
            if (fieldName.equals(cateHeader[i])) {
                index = i;
            }
        }

        return cateFieldStat[index];
    }

    // private String[] getCateFieldData(String fieldName) {
    //
    // Integer index = null;
    // for (int i = 0; i < cateHeader.length; i++) {
    // if (fieldName.equals(cateHeader[i])) {
    // index = i;
    // }
    // }
    //
    // String[] fieldData = new String[data.length];
    // for (int i = 0; i < data.length; i++) {
    // fieldData[i] = cateData[i][index];
    // }
    // return fieldData;
    // }

    private CateFieldStat findCateFieldStat(String fieldName, String[] fieldData) {

        Map<String, Integer> cateCountMap = new HashMap<String, Integer>();

        for (int i = 0; i < fieldData.length; i++) {
            String dataValue = fieldData[i];
            Integer count = cateCountMap.get(dataValue);
            if (count == null) {
                count = 0;
            } else {
                count++;
            }
            cateCountMap.put(dataValue, count);
        }

        List<String> levels = new ArrayList<String>();
        levels.addAll(cateCountMap.keySet());

        return new CateFieldStat(fieldName, levels, cateCountMap);
    }

    public int getRecordNum() {
        return rowNum;
    }
}
