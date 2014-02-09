package org.ninjacat.easyminer.algorithm.tree.binary;

import java.util.ArrayList;
import java.util.List;

import org.ninjacat.easyminer.io.FieldData;

public final class BinaryNode {

    public BinaryNode(CategoryBinarySplit bestSplit) {
        // TODO Auto-generated constructor stub
    }

    String score;

    int nodeId;

    boolean hasChildren;

    BinaryNode leftNode, rightNode;

    private BinaryNode buildNode(List<FieldData> data, List<String> splitFields) {

        List<CategoryBinarySplit> cateSplits = findCateSplits(splitFields);
        List<Double> splitsGini = new ArrayList<Double>();

        Double minGini = null;
        CategoryBinarySplit bestSplit = null;

        for (CategoryBinarySplit split : cateSplits) {
            Double gini = split.computeGini(data);
            if (minGini == null || gini < minGini) {
                minGini = gini;
                bestSplit = split;
            }
        }

        BinaryNode currentNode = new BinaryNode(bestSplit);
        return currentNode;
    }

    private List<CategoryBinarySplit> findCateSplits(List<String> splitFields) {

        // TODO:
        // We should use binary map to indicate the split to be efficient

        return null;
    }
}
