package org.ninjacat.easyminer.algorithm.svm;

import java.util.Arrays;
import java.util.Vector;

public class RecordData {

    Vector<Double> data = new Vector<Double>();

    private RecordData(Double[] inputRecord) {
        super();
        data.addAll(Arrays.asList(inputRecord));
    }

}
