package org.ninjacat.easyminer.profiling;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class MemoryUtilityTest extends TestCase {
    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public MemoryUtilityTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(MemoryUtilityTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {

        Runtime runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        runtime.gc();
        runtime.gc();
        // Calculate the used memory
        long memory = runtime.totalMemory() - runtime.freeMemory();

        System.out.println("Before : " + bytesToMegabytes(memory));
        // System.out.println(MemoryUtility.getObjectSize(new Double[10]));
        System.out.println("Hello");
        System.out.println(ObjectSizeFetcher.getObjectSize(new Double(0)));
        // ObjectSizeFetcher.getObjectSize(new Double(0));

        final String[] strings = { "a", "bb", "Dustin" };
        final String[] threeStrings = new String[10];
        final String[] moreStrings = new String[1000];
        final String[] manyStrings = new String[2000];

        System.out.println("One Double " + ObjectSizeFetcher.getObjectSize(new Double(1)));
        System.out.println("One Double " + Double.SIZE);
        System.out.println("One string " + ObjectSizeFetcher.getObjectSize(new String("a")));
        System.out.println(ObjectSizeFetcher.getObjectSize(strings));
        System.out.println(ObjectSizeFetcher.getObjectSize(threeStrings));
        System.out.println(ObjectSizeFetcher.getObjectSize(moreStrings));
        System.out.println(ObjectSizeFetcher.getObjectSize(manyStrings));

        List<String> stringList = new ArrayList<String>();

        for (int i = 0; i < 100000; i++) {
            stringList.add(new String(String.valueOf(i)));
        }
        System.out.println("String List " + ObjectSizeFetcher.getObjectSize(stringList) + " "
            + stringList.size());
        // Get the Java runtime

        runtime.gc();
        runtime.gc();
        runtime.gc();
        // Calculate the used memory
        memory = runtime.totalMemory() - runtime.freeMemory();

        System.out.println("after : " + bytesToMegabytes(memory));

    }

    private static final long MEGABYTE = 1024L;// * 1024L;

    public static long bytesToMegabytes(long bytes) {
        return bytes / MEGABYTE;
    }
}
