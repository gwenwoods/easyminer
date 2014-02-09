package org.ninjacat.easyminer.transformations.normcontinuous;

import java.io.BufferedReader;

import org.dmg.pmml42.DerivedFieldDocument;
import org.dmg.pmml42.NormContinuousDocument;
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

    public static DerivedFieldDocument exportPMML(DoubleData input, DoubleData derived) {

        DerivedFieldDocument pmmlDFDoc = DerivedFieldDocument.Factory.newInstance();
        DerivedFieldDocument.DerivedField pmmlDF = pmmlDFDoc.addNewDerivedField();

        pmmlDF.setName(derived.getFieldName());

        NormContinuousDocument.NormContinuous pmmlNC = pmmlDF.addNewNormContinuous();

        pmmlNC.addNewLinearNorm();
        pmmlNC.addNewLinearNorm();

        return pmmlDFDoc;
    }
}
