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
public class CategoryBinarySplitTest extends TestCase {
    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public CategoryBinarySplitTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(CategoryBinarySplitTest.class);
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
        List<String> leftNodeCategories = new ArrayList<String>();
        leftNodeCategories.add("A1");
        CategoryBinarySplit split = new CategoryBinarySplit("inputA", leftNodeCategories);

        double gini = split.computeGini(data);

        System.out.println(gini);

        assertTrue(true);
    }
}
