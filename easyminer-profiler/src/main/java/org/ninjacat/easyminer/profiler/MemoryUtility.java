package org.ninjacat.easyminer.profiling;

import java.lang.instrument.Instrumentation;

public class MemoryUtility {

    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
    }

    public static long getObjectSize(Object o) {
        return instrumentation.getObjectSize(o);
    }
}
