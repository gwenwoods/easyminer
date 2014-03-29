package org.ninjacat.easyminer.algorithm.tree.binary;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.xmlbeans.XmlOptions;
import org.dmg.pmml42.NodeDocument;
import org.dmg.pmml42.PMMLDocument;
import org.easyminer.io.FieldData;
import org.easyminer.io.StringData;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class BinaryNodeTest extends TestCase {
    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public BinaryNodeTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(BinaryNodeTest.class);
    }

    /**
     * Rigourous Test :-)
     * 
     * @throws IOException
     */
    public void testApp() throws IOException {

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
        data.add(predicateFieldData);

        BinaryNode node = new BinaryNode(new TruePredicate(), data, targetData);

        Map<String, String> suggestedNamespaces = new HashMap<String, String>();
        suggestedNamespaces.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
        suggestedNamespaces.put(PMMLDocument.type.getDocumentElementName().getNamespaceURI(), "");

        NodeDocument nodeDoc = NodeDocument.Factory.newInstance();
        nodeDoc.addNewNode();
        nodeDoc.setNode(node.exportPMML());

        XmlOptions TEXT_SAVE_XML_OPTIONS = new XmlOptions();
        TEXT_SAVE_XML_OPTIONS.setSaveOuter();
        TEXT_SAVE_XML_OPTIONS.setSaveAggressiveNamespaces();
        TEXT_SAVE_XML_OPTIONS.setSavePrettyPrint();
        TEXT_SAVE_XML_OPTIONS.setCharacterEncoding("UTF-8");
        TEXT_SAVE_XML_OPTIONS.setSaveSuggestedPrefixes(suggestedNamespaces);
        InputStream newStr = nodeDoc.newInputStream(TEXT_SAVE_XML_OPTIONS);

        StringWriter writer = new StringWriter();
        IOUtils.copy(newStr, writer, "UTF-8");

        System.out.println(writer.toString());
        return;
    }
}
