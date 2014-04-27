package org.easyminer.algorithm.binarytree;

import java.io.File;
import java.io.IOException;

import org.easyminer.io.CSVReader;
import org.easyminer.io.data.CompactData;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class BinaryClassificationTreeTest extends TestCase {
    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public BinaryClassificationTreeTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(BinaryClassificationTreeTest.class);
    }

    /**
     * Rigourous Test :-)
     * 
     * @throws IOException
     */
    public void testTree() throws IOException {

        File directory = new File("..");
        String rootPath = directory.getCanonicalPath();
        String dataPath = rootPath + "/src/test/resources/data/audit.csv";

        // TODO: create internal data set reader
        CompactData data = CSVReader.read(dataPath);

        BinaryClassificationTree tree = new BinaryClassificationTree();
        // tree.train(data);

    }
}
