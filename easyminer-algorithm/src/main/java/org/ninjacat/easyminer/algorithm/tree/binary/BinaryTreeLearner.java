package org.ninjacat.easyminer.algorithm.tree.binary;

import java.util.List;

import org.ninjacat.easyminer.io.FieldData;

public class BinaryTreeLearner {

    BinaryTree buildTree(List<FieldData> data) {

        String targetField = findTarget(data);

        List<String> splitFields = colloectSplitFields(data);

        BinaryNode rootNode = buildNode(data, splitFields);

        return null;
    }

    private List<String> colloectSplitFields(List<FieldData> data) {
        // TODO Auto-generated method stub
        return null;
    }

    private String findTarget(List<FieldData> data) {
        // TODO Auto-generated method stub
        return null;
    }

    private BinaryNode buildNode(List<FieldData> data, List<String> splitFields) {

        List<CategoryBinarySplit> cateSplits = findCateSplits(splitFields);

        return null;
    }

    private List<CategoryBinarySplit> findCateSplits(List<String> splitFields) {

        // TODO:
        // We should use binary map to indicate the split to be efficient

        return null;
    }
}
