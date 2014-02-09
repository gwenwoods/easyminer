package org.ninjacat.easyminer.transformations.normcontinuous;

import org.ninjacat.easyminer.io.DoubleData;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class RangeNormalizerTest extends TestCase {
    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public RangeNormalizerTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(RangeNormalizerTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {

        DoubleData A = new DoubleData("fieldA");

        for (int i = 0; i < 100; i++) {
            A.addRecord((double) i);
        }

        DoubleData derivedA = RangeNormalizer.rangeNormalizer(-1.0, 1.0, A);

        // for (Double dRecord : derivedA.getData()) {
        assertTrue(derivedA.getData().get(0) - (-1.0) < 0.000001);
        // }

        String xformPMML = RangeNormalizer.exportPMML(A, derivedA);

        assertEquals("<DerivedField d_fieldA   <NormContinuous field = fieldA", xformPMML);
        assertTrue(true);
    }
}
