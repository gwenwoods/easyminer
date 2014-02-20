package org.ninjacat.easyminer.algorithm.tree.binary;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ChildRecordDistsTest extends TestCase {
    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public ChildRecordDistsTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(ChildRecordDistsTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {

        Map<String, Integer> leftRecordsMap = new HashMap<String, Integer>();

        leftRecordsMap.put("A", 1);
        leftRecordsMap.put("B", 2);
        leftRecordsMap.put("C", 3);
        leftRecordsMap.put("D", 4);

        Map<String, Integer> rightRecordsMap = new HashMap<String, Integer>();

        rightRecordsMap.put("A", 4);
        rightRecordsMap.put("B", 4);
        rightRecordsMap.put("C", 2);

        // -----------------------------

        ChildRecordDists childRecordDists = new ChildRecordDists(leftRecordsMap, rightRecordsMap, 10, 10);
        Double impurity = childRecordDists.getChildImpurity();

        // left probs: 0.1, 0.2, 0.3. 0.4
        // left impurity = 0.02 + 0.03 + 0.04 +0.06 + 0.08 + 0.12 =0.35
        // right probs: 0.4, 0.4, 0.2
        // right impurity = 0.16 + 0.08 + 0.08 = 0.32
        // impurity = 0.35 * 0.5 + 0.32 * 0.5 = 0.335

        assertTrue(Math.abs(impurity - 0.335) < 0.000001);
    }
}
