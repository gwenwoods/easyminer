package org.ninjacat.easyminer.transformations;

import java.io.BufferedReader;

import org.ninjacat.easyminer.io.FileBufferedReader;

public class NormContinuous {

    String baseFilename;
    String fieldName;

    public void transform(String baseFilename, String inputFieldName, String outputFieldName) {

        // Double min = findMin();

        BufferedReader csvBufferedReader = FileBufferedReader.read(inputFieldName);

    }

}
