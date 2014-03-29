package org.ninjacat.easyminer.transformations.apply.mathfunction;

import org.dmg.pmml42.ApplyDocument;
import org.dmg.pmml42.DerivedFieldDocument;
import org.dmg.pmml42.FieldRefDocument;
import org.easyminer.io.DoubleData;

public class PlusFunction {

    String baseFilename;

    String fieldName;

    public static DoubleData plus(DoubleData input1, DoubleData input2) {

        DoubleData xformData = new DoubleData("d_" + input1.getFieldName() + "_" + input2.getFieldName());

        // TODO:
        // check input1 and input2 have the same size
        for (int i = 0; i < input1.getData().size(); i++) {
            Double xformRecord = input1.getData().get(i) + input2.getData().get(i);
            xformData.addRecord(xformRecord);
        }

        return xformData;
    }

    public static DerivedFieldDocument exportPMML(DoubleData input1, DoubleData input2, DoubleData derived) {

        DerivedFieldDocument pmmlDFDoc = DerivedFieldDocument.Factory.newInstance();
        DerivedFieldDocument.DerivedField pmmlDF = pmmlDFDoc.addNewDerivedField();

        pmmlDF.setName(derived.getFieldName());

        ApplyDocument.Apply pmmlApply = pmmlDF.addNewApply();

        pmmlApply.setFunction("+");
        FieldRefDocument.FieldRef pmmlFR1 = pmmlApply.addNewFieldRef();
        FieldRefDocument.FieldRef pmmlFR2 = pmmlApply.addNewFieldRef();

        pmmlFR1.setField(input1.getFieldName());
        pmmlFR2.setField(input2.getFieldName());

        return pmmlDFDoc;
    }
}
