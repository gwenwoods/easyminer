package org.ninjacat.easyminer.transformations.normcontinuous;

import java.io.BufferedReader;

import org.ninjacat.easyminer.io.DoubleData;
import org.ninjacat.easyminer.io.FileBufferedReader;

public class RangeNormalizer {

    String baseFilename;

    String fieldName;

    public void transform(String baseFilename, String inputFieldName, String outputFieldName) {

        // Double min = findMin();

        BufferedReader csvBufferedReader = FileBufferedReader.read(inputFieldName);

    }

    public static DoubleData rangeNormalizer(Double min, Double max, DoubleData input) {

        DoubleData xformData = new DoubleData("d_" + input.getFieldName());

        Double dataRange = input.getMax() - input.getMin();

        Double normalizerRange = max - min;

        if (dataRange == 0) {
            // TODO:
            // error message here
        }

        if (normalizerRange == 0) {
            // TODO:
            // error message here
        }

        for (Double record : input.getData()) {
            Double xformRecord = min + normalizerRange * (record - input.getMin()) / dataRange;
            xformData.addRecord(xformRecord);
        }

        return xformData;

    }

    public static String exportPMML(DoubleData input, DoubleData derived) {

        StringBuilder pmmlStr = new StringBuilder();
        pmmlStr.append("<DerivedField " + derived.getFieldName());
        pmmlStr.append("   <NormContinuous field = " + input.getFieldName());

        return pmmlStr.toString();
    }
}
