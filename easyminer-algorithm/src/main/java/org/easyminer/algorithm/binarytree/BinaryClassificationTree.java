package org.easyminer.algorithm.binarytree;

import org.easyminer.io.data.Data1;
import org.ninjacat.easyminer.algorithm.tree.binary.TruePredicate;

public class BinaryClassificationTree {

    public void train(Data1 data) {

        int rowNum = data.getTargetVector().size();
        
        boolean[] nodeRecords = new boolean[rowNum];
        data.get
        BinaryTreeNode treeNode =
            new BinaryTreeNode(new TruePredicate(), data.getCateData(), data.getNumericData(),
                data.getTargetVector(), nodeRecords, cateFieldsInfo);
    }
}
