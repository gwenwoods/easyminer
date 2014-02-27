package org.ninjacat.easyminer.algorithm.regression;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class RegressionLearnerTest extends TestCase {
    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public RegressionLearnerTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(RegressionLearnerTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {

        RegressionLearner regLearner = new RegressionLearner();

        Double[][] data = new Double[10][1];
        Double[] y = new Double[10];

        data[0][0] = 1.0;
        data[1][0] = 2.1;
        data[2][0] = 3.2;
        data[3][0] = 4.3;
        data[4][0] = 4.9;
        data[5][0] = 6.1;
        data[6][0] = 6.9;
        data[7][0] = 8.2;
        data[8][0] = 9.1;
        data[9][0] = 9.7;

        y[0] = 101.1;
        y[1] = 202.5;
        y[2] = 298.5;
        y[3] = 405.12;
        y[4] = 502.3;
        y[5] = 610.1;
        y[6] = 706.1;
        y[7] = 800.1;
        y[8] = 890.4;
        y[9] = 999.3;

        regLearner.fitRegression(10, 1, data, y);

    }
}
