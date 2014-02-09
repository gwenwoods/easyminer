package org.ninjacat.easyminer.transformations.apply.mathfunction;

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
public class PlusFunctionTest extends TestCase {
    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public PlusFunctionTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(PlusFunctionTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {

        DoubleData A = new DoubleData("fieldA");

        for (int i = 0; i < 100; i++) {
            A.addRecord((double) i);
        }

        DoubleData B = new DoubleData("fieldB");

        for (int i = 0; i < 100; i++) {
            B.addRecord((double) i * 2);
        }

        DoubleData derived = PlusFunction.plus(A, B);

        // for (Double dRecord : derivedA.getData()) {
        assertTrue(Math.abs(derived.getData().get(1) - 3.0) < 0.000001);
        // }

        DerivedFieldDocument pmmlDF = PlusFunction.exportPMML(A, B, derived);

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
                sb.append(line).append("\n");

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

        System.out.println(sb.toString().replace("xmlns=\"http://www.dmg.org/PMML-4_2\"", ""));
        // assertEquals("<DerivedField d_fieldA   <NormContinuous field = fieldA", xformPMML);
        // assertTrue(true);
    }
}
