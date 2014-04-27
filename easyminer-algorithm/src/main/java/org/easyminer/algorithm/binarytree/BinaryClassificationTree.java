package org.easyminer.algorithm.binarytree;

import java.util.ArrayList;
import java.util.List;

import org.easyminer.io.data.CateFieldStat;
import org.easyminer.io.data.Data1;
import org.ninjacat.easyminer.algorithm.tree.binary.TruePredicate;

public class BinaryClassificationTree {

    public void train(Data1 data) {

        int rowNum = data.getCateTargetVector().size();

        int cateColumnNum = data.getCateData().size();
        int numericColumnNum = data.getNumericData().size();

        String[][] cateData = new String[rowNum][cateColumnNum];
        Double[][] numericData = new Double[rowNum][cateColumnNum];
        String[] targetVector = new String[rowNum];
        boolean[] nodeRecords = new boolean[rowNum];
        CateFieldStat[] cateFieldStats = new CateFieldStat[cateColumnNum];

        List<String> cateHeaders = new ArrayList<String>(data.getCateData().keySet());
        List<String> numericHeaders = new ArrayList<String>(data.getNumericData().keySet());

        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < cateColumnNum; j++) {
                String header = cateHeaders.get(j);
                cateData[i][j] = data.getCateData().get(header).get(i);
            }
            for (int j = 0; j < numericColumnNum; j++) {
                String header = numericHeaders.get(j);
                numericData[i][j] = data.getNumericData().get(header).get(i);
            }
            nodeRecords[i] = true;
            targetVector[i] = data.getCateTargetVector().get(i).toString();
        }

        for (int j = 0; j < cateColumnNum; j++) {
            String header = cateHeaders.get(j);
            cateFieldStats[j] = data.getCateFieldStat(header);
        }

        BinaryTreeNode treeNode =
            new BinaryTreeNode(new TruePredicate(), cateData, numericData, targetVector, nodeRecords,
                cateFieldStats);

        treeNode.exportPMML();
    }
}
