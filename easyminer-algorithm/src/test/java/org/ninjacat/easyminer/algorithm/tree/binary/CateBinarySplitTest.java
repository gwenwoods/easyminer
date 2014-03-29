package org.ninjacat.easyminer.algorithm.tree.binary;

import java.util.ArrayList;
import java.util.List;

import org.easyminer.io.FieldData;
import org.easyminer.io.StringData;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class CateBinarySplitTest extends TestCase {
    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public CateBinarySplitTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(CateBinarySplitTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {

        StringData targetData = new StringData("target");
        targetData.addRecord("T1");
        targetData.addRecord("T2");
        targetData.addRecord("T2");
        targetData.addRecord("T1");
        targetData.addRecord("T1");
        targetData.setTarget(true);

        StringData predicateFieldData = new StringData("inputA");
        predicateFieldData.addRecord("A1");
        predicateFieldData.addRecord("A2");
        predicateFieldData.addRecord("A3");
        predicateFieldData.addRecord("A1");
        predicateFieldData.addRecord("A3");

        List<FieldData> data = new ArrayList<FieldData>();
        data.add(targetData);
        data.add(predicateFieldData);

        // -----------------------------
        // List<String> leftNodeCategories = new ArrayList<String>();
        // leftNodeCategories.add("A1");
        CateBinarySplit split = new CateBinarySplit("inputA", 2);

        // split 2='10'= '010'
        // left child category : A2
        // left records count: T2:1
        // left child impurity = 0
        // right child category: A1, A3
        // right records count: T1:3 T2:1
        // right child impurity =0.1875
        // P_l = 0.2 P_r =0.8
        // impurity = 0.2 * 0 + 0.8 * 0.1875 = 0.15

        assertEquals("[A2]", split.getLeftCateSet(predicateFieldData.getCateLevels()).toString());

        Double childImpurity = split.childImpurity(predicateFieldData, targetData);
        assertTrue(Math.abs(childImpurity - 0.15) < 0.000001);
    }
}
