package org.easyminer.io;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import org.easyminer.io.data.CompactData;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class CSVReaderTest extends TestCase {
    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public CSVReaderTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(CSVReaderTest.class);
    }

    /**
     * Rigourous Test :-)
     * 
     * @throws IOException
     */
    public void testCSVReader() throws IOException {

        File directory = new File("..");
        String rootPath = directory.getCanonicalPath();
        String dataPath = rootPath + "/src/test/resources/data/audit.csv";

        CompactData data = CSVReader.read(dataPath);

        for (int i = 0; i < data.getColumnType().length; i++) {
            System.out.print(data.getColumnType()[i] + ",");
        }

        System.out.println();
        // --------------------
        // cate data
        for (int j = 0; j < data.getCateHeader().length; j++) {
            System.out.print(data.getCateHeader()[j] + ",");
        }
        String[][] cateData = data.getCateData();

        for (int i = 0; i < cateData.length; i++) {
            for (int j = 0; j < cateData[0].length; j++) {
                System.out.print(cateData[i][j] + ",");
            }
            System.out.print("\n");
        }

        // ------------------------
        // numeric data
        for (int j = 0; j < data.getNumericHeader().length; j++) {
            System.out.print(data.getNumericHeader()[j] + ",");
        }

        Double[][] numericData = data.getNumericData();
        for (int i = 0; i < numericData.length; i++) {
            for (int j = 0; j < numericData[0].length; j++) {
                System.out.print(numericData[i][j] + ",");
            }
            System.out.print("\n");
        }
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp1() {

        Pattern pattern = Pattern.compile("^\".*\"$");
        Pattern quotPattern = Pattern.compile("^\"|\"$");

        String[] input = new String[4];
        input[0] = "\"College\"";
        input[1] = "College\"";
        input[2] = "\"College";
        input[3] = "College";

        // pattern.
        for (int i = 0; i < 4; i++) {
            if (pattern.matcher(input[i]).find()) {
                System.out.println(quotPattern.matcher(input[i]).replaceAll(""));
            } else {
                System.out.println(input[i]);
            }
        }
    }
}
