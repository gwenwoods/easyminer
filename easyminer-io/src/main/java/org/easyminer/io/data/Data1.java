package org.easyminer.io.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * 
 * Experimental set. Might be deprecated later - Apr 14.
 * 
 * The data.
 */
public final class Data1 {

    Map<String, Vector<String>> data = new LinkedHashMap<String, Vector<String>>();

    Map<String, Vector<String>> cateData = new LinkedHashMap<String, Vector<String>>();
    Map<String, Vector<Double>> numericData = new LinkedHashMap<String, Vector<Double>>();

    Map<String, CateFieldStat> cateFieldStats = new LinkedHashMap<String, CateFieldStat>();

    Map<String, String> columnTypeMap = new LinkedHashMap<String, String>();

    String targetFieldName;
    String targetDataType;
    Vector<? extends Object> cateTargetVector;
    Vector<Double> numericTargetVector;
    // String[] columnType;

    // String[] cateHeader;
    // String[] numericHeader;

    // CateFieldStat[] cateFieldStat;

    int cateFieldNum;
    int numericFieldNum;

    // String baseFilename;
    // List<String> headFields = new ArrayList<String>();

    public Data1(String[][] data, String[] header) {

        int rowNum = data.length;
        int columnNum = data[0].length;

        // columnType = new String[columnNum];
        numericFieldNum = columnNum;

        for (int j = 0; j < columnNum; j++) {
            // columnType[j] = "Double";

            columnTypeMap.put(header[j], "Double");

            for (int i = 0; i < rowNum; i++) {

                if (data[i][j] != null) {
                    try {
                        Double.parseDouble(data[i][j]);
                    } catch (NumberFormatException e) {
                        // columnType[j] = "String";
                        columnTypeMap.put(header[j], "String");
                        cateFieldNum++;
                        numericFieldNum--;
                        break;
                    }
                }
            }
        }

        // cateData = new String[rowNum][cateFieldNum];

        // numericData = new Double[rowNum][numericFieldNum];

        // cateHeader = new String[cateFieldNum];
        // numericHeader = new String[numericFieldNum];

        // cateFieldStat = new CateFieldStat[cateFieldNum];
        int cateIndex = 0;
        // int numericIndex = 0;

        // System.out.println("meow " + cateData[1][1]);

        for (int j = 0; j < columnNum; j++) {

            String colHeader = header[j];
            if ("Double".equals(columnTypeMap.get(colHeader))) {
                Vector<Double> numericVector = new Vector<Double>(rowNum);
                for (int i = 0; i < rowNum; i++) {

                    if (data[i][j] != null) {
                        try {
                            Double value = Double.parseDouble(data[i][j]);
                            numericVector.add(value);
                            // numericData[i][numericIndex] = value;
                            // numericHeader[numericIndex] = header[j];
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            // should not happen here
                        }
                    }
                }
                numericData.put(header[j], numericVector);
                // numericIndex++;
            } else {
                Vector<String> cateVector = new Vector<String>(rowNum);
                // String[] fieldData = new String[rowNum];
                for (int i = 0; i < rowNum; i++) {
                    cateVector.add(data[i][j]);
                    // if (data[i][j] != null) {
                    // cateData[i][cateIndex] = data[i][j];
                    // cateHeader[cateIndex] = header[j];
                    // fieldData[i] = data[i][j];

                    // }
                }
                cateData.put(header[j], cateVector);
                cateFieldStats.put(header[j], findCateFieldStat(header[j], cateVector));
                // cateFieldStat[cateIndex] = findCateFieldStat(header[j], cateVector);
                // cateIndex++;
            }

        }

    }

    //
    // public String[] getCateHeader() {
    // return cateData.keySet();
    // }
    //
    // public String[] getNumericHeader() {
    // return numericHeader;
    // }

    public Map<String, Vector<String>> getCateData() {
        return cateData;
    }

    public Map<String, Vector<Double>> getNumericData() {
        return numericData;
    }

    // public void addHeaderField(String fieldName) {
    // headFields.add(fieldName);
    // }
    //
    // public List<String> getHeaderFields() {
    // return Collections.unmodifiableList(headFields);
    // }

    // public String[] getColumnType() {
    // return columnType;
    // }

    public CateFieldStat getCateFieldStat(String fieldName) {

        return cateFieldStats.get(fieldName);
        // Integer index = null;
        //
        // for (int i = 0; i < cateHeader.length; i++) {
        // if (fieldName.equals(cateHeader[i])) {
        // index = i;
        // }
        // }
        //
        // return cateFieldStat[index];
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

    private CateFieldStat findCateFieldStat(String fieldName, Vector<String> fieldData) {

        Map<String, Integer> cateCountMap = new HashMap<String, Integer>();

        for (int i = 0; i < fieldData.size(); i++) {
            String dataValue = fieldData.get(i);
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

    public <T> void setTarget(String fieldName, Class<T> T) {

        if (fieldName.equals(targetFieldName)) {
            return;
        }

        if (targetFieldName != null) {
            // if ("Double".equals(targetDataType)) {
            if (Double.class == T) {
                numericData.put(targetFieldName, numericTargetVector);
            } else {
                cateData.put(targetFieldName, (Vector<String>) cateTargetVector);
            }
        }

        targetFieldName = fieldName;
        targetDataType = columnTypeMap.get(fieldName);
        if ("Double".equals(targetDataType)) {
            numericTargetVector = numericData.get(fieldName);

            if (String.class == T) {
                cateTargetVector = new Vector<String>();
                for (int i = 0; i < numericTargetVector.size(); i++) {

                }
            }
            numericData.remove(fieldName);
        } else {
            cateTargetVector = cateData.get(fieldName);
            cateData.remove(fieldName);
        }
    }

    public Vector<? extends Object> getCateTargetVector() {
        return cateTargetVector;
    }
}
