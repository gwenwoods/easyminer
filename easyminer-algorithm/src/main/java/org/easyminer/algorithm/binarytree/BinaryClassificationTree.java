package org.easyminer.algorithm.binarytree;

import java.util.Arrays;
import java.util.List;

import org.easyminer.io.data.CateFieldStat;
import org.easyminer.io.data.CompactData;
import org.ninjacat.easyminer.algorithm.tree.binary.TruePredicate;

public class BinaryClassificationTree {

    public void train(CompactData data) {

        int rowNum = data.getRecordNum();

        int cateColumnNum = data.getCateData()[0].length;
        int numericColumnNum = data.getNumericData()[0].length;

        String[][] cateData = new String[rowNum][cateColumnNum];
        Double[][] numericData = new Double[rowNum][cateColumnNum];
        String[] targetVector = new String[rowNum];
        boolean[] nodeRecords = new boolean[rowNum];
        CateFieldStat[] cateFieldStats = new CateFieldStat[cateColumnNum];

        List<String> cateHeaders = Arrays.asList(data.getCateHeader());
        List<String> numericHeaders = Arrays.asList(data.getNumericHeader());

        // for (int i = 0; i < rowNum; i++) {
        // for (int j = 0; j < cateColumnNum; j++) {
        // String header = cateHeaders.get(j);
        // cateData[i][j] = data.getCateData().get(header).get(i);
        // }
        // for (int j = 0; j < numericColumnNum; j++) {
        // String header = numericHeaders.get(j);
        // numericData[i][j] = data.getNumericData().get(header).get(i);
        // }
        // nodeRecords[i] = true;
        // targetVector[i] = data.getCateTargetVector().get(i).toString();
        // }

        for (int j = 0; j < cateColumnNum; j++) {
            String header = cateHeaders.get(j);
            cateFieldStats[j] = data.getCateFieldStat(header);
        }

        BinaryTreeNode treeNode =
            new BinaryTreeNode(new TruePredicate(), data.getCateData(), data.getNumericData(), targetVector,
                nodeRecords, cateFieldStats);

        treeNode.exportPMML();
    }
}
