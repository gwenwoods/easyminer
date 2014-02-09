package org.ninjacat.easyminer.transformations.normcontinuous;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.xmlbeans.XmlOptions;
import org.dmg.pmml42.DerivedFieldDocument;
import org.dmg.pmml42.PMMLDocument;
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

        DerivedFieldDocument pmmlDF = RangeNormalizer.exportPMML(A, derivedA);

        Map<String, String> suggestedNamespaces = new HashMap<String, String>();
        suggestedNamespaces.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
        suggestedNamespaces.put(PMMLDocument.type.getDocumentElementName().getNamespaceURI(), "");

        XmlOptions TEXT_SAVE_XML_OPTIONS = new XmlOptions();
        TEXT_SAVE_XML_OPTIONS.setSaveOuter();
        TEXT_SAVE_XML_OPTIONS.setSaveAggressiveNamespaces();
        TEXT_SAVE_XML_OPTIONS.setSavePrettyPrint();
        TEXT_SAVE_XML_OPTIONS.setCharacterEncoding("UTF-8");
        TEXT_SAVE_XML_OPTIONS.setSaveSuggestedPrefixes(suggestedNamespaces);
        InputStream newStr = pmmlDF.newInputStream(TEXT_SAVE_XML_OPTIONS);

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(newStr));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println(sb.toString());
        // assertEquals("<DerivedField d_fieldA   <NormContinuous field = fieldA", xformPMML);
        // assertTrue(true);
    }
}
