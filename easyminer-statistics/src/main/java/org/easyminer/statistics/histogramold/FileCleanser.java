package org.easyminer.statistics.histogramold;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class FileCleanser {

    private final String inputFileName;
    private final String outputFileName;
    private List<String> headerList = new ArrayList<String>();
    private final List<String> numericFieldList = new ArrayList<String>();
    private final List<String> cateFieldList = new ArrayList<String>();

    private final Map<String, List<String>> fieldCategoriesMap = new HashMap<String, List<String>>();
    private final Map<String, DataRange> fieldDataRangeMap = new HashMap<String, DataRange>();
    private int recordNum = 0;

    public FileCleanser(String inputFileName, String outputFileName) {
        super();
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
    }

    public void clean() throws IOException {

        CSVParser cp = new CSVParser();

        File inFile = new File(inputFileName);
        File outFile = new File(outputFileName);

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inFile)));
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(outFile));

        // process header
        String header = br.readLine();
        header = header.trim();
        // String[] headValues;
        // List<String> headerList = new ArrayList<String>();
        headerList = cp.parseLineToList(header);
        // headValues = header.split(",");
        for (String value : headerList) {
            value = value.trim();
            dos.writeBytes(value + ",");

            // headerList.add(value);
            fieldCategoriesMap.put(value, new ArrayList<String>());
            fieldDataRangeMap.put(value, new DataRange(999999999, -999999999));
        }
        dos.writeBytes("\n");

        // process data
        String inputLine;
        while ((inputLine = br.readLine()) != null) {
            recordNum++;

            List<String> stringList = new ArrayList<String>();
            inputLine = inputLine.trim();
            // stringValues = inputLine.split(",");
            stringList = cp.parseLineToList(inputLine);

            for (int i = 0; i < headerList.size(); i++) {
                String value = headerList.get(i);
                value = value.trim();
                dos.writeBytes(value + ",");
                String fieldName = headerList.get(i);

                try {
                    if (value.length() == 0) {
                        continue;
                    }
                    double dValue = Double.valueOf(value);
                    DataRange range = fieldDataRangeMap.get(fieldName);
                    double min = range.min;
                    double max = range.max;
                    if (dValue < min) {
                        min = dValue;
                    }
                    if (dValue > max) {
                        max = dValue;
                    }
                    fieldDataRangeMap.put(fieldName, new DataRange(min, max));
                } catch (Exception e) {

                    List<String> categories = fieldCategoriesMap.get(fieldName);

                    if (!categories.contains(value)) {
                        categories.add(value);
                    }
                    fieldCategoriesMap.put(fieldName, categories);
                }
            }
            dos.writeBytes("\n");
        }

        br.close();

        for (String fieldName : headerList) {
            if (fieldCategoriesMap.get(fieldName).size() > 0) {
                cateFieldList.add(fieldName);
            } else {
                numericFieldList.add(fieldName);
            }
        }
    }

    public final Map<String, DataRange> getFieldDataRangeMap() {
        return fieldDataRangeMap;
    }

    public final List<String> getNumericFieldList() {
        return numericFieldList;
    }

    public final List<String> getCateFieldList() {
        return cateFieldList;
    }

    public final int getRecordNum() {
        return recordNum;
    }

    public final List<String> getHeaderList() {
        return Collections.unmodifiableList(headerList);
    }

    public final List<String> getCategoriesForField(String fieldName) {
        return fieldCategoriesMap.get(fieldName);
    }
}
