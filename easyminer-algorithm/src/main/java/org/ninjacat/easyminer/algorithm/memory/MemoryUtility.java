package org.ninjacat.easyminer.algorithm.memory;

import java.lang.instrument.Instrumentation;

import org.ninjacat.easyminer.algorithm.regression.RegressionLearner;

public class MemoryUtility {

    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
    }

    public static long getObjectSize(Object o) {
        return instrumentation.getObjectSize(o);
    }

    public static void main() {
        System.out.println(getObjectSize(new RegressionLearner()));
    }
}
