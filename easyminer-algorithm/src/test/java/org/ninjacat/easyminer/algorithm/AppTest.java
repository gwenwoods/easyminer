package org.ninjacat.easyminer.algorithm;

import org.ninjacat.easyminer.algorithm.regression.RegressionLearner;
import org.ninjacat.easyminer.profiler.ObjectSizeFetcher;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        // TODO: fix agent path
        // -javaagent:/Users/wenlin/buenofelino/easyminer/easyminer-profiler/target/easyminer-profiler-1.0-SNAPSHOT.jar

        System.out.println("Hello");
        System.out.println(ObjectSizeFetcher.getObjectSize(new RegressionLearner()));
    }
}
