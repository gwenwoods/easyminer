package org.ninjacat.easyminer.algorithm.tree.binary;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ListTest extends TestCase {
    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public ListTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(ListTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {

        List<Double> originalList = new ArrayList<Double>();

        originalList.add(1.0);
        originalList.add(2.0);
        originalList.add(3.0);
        originalList.add(4.0);
        originalList.add(5.0);

        List<Double> newList = new ArrayList<Double>();
        newList.add(originalList.get(0));
        newList.add(originalList.get(1));
        newList.add(originalList.get(2));

        // Double a = originalList.get(0);
        // a = a + 100.0;
        // originalList.add(0, 100.0);
        originalList.clear();

        System.out.println(originalList);
        System.out.println(newList);

    }
}
