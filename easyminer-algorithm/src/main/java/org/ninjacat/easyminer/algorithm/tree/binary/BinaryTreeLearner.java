package org.ninjacat.easyminer.algorithm.tree.binary;

import java.util.ArrayList;
import java.util.List;

import org.ninjacat.easyminer.io.FieldData;

public class BinaryTreeLearner {

    public static BinaryTree buildTree(List<FieldData> data) {

        List<Boolean> usedRecordIndex = new ArrayList<Boolean>();
        for (int i = 0; i < data.size(); i++) {
            usedRecordIndex.add(Boolean.TRUE);
        }

        BinaryNode rootNode = new BinaryNode(new TruePredicate(), data, usedRecordIndex);

        return new BinaryTree(rootNode);
    }
}
