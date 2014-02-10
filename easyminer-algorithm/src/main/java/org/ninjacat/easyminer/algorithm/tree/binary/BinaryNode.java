package org.ninjacat.easyminer.algorithm.tree.binary;

import java.util.ArrayList;
import java.util.List;

import org.ninjacat.easyminer.io.FieldData;

public final class BinaryNode {

    String score;

    int nodeId;

    Predicate predicate;

    boolean hasChildren;

    BinaryNode leftChild, rightChild;

    public BinaryNode(Predicate predicate, List<FieldData> data, List<Boolean> usedDataIndex) {
        this.predicate = predicate;

        List<BinaryNode> childrenNodes = splitToChildren(data);

        if (childrenNodes.size() == 2) {
            this.leftChild = childrenNodes.get(0);
            this.rightChild = childrenNodes.get(1);
            this.hasChildren = true;
        }

        // TODO: compute score/ scoreDistribution from data here
    }

    private List<BinaryNode> splitToChildren(List<FieldData> data) {

        List<CategoryBinarySplit> allPossibleCateSplits = findCateSplits(data);

        Double minGini = null;
        CategoryBinarySplit bestSplit = null;

        for (CategoryBinarySplit split : allPossibleCateSplits) {
            Double gini = split.computeGini(data);
            if (minGini == null || gini < minGini) {
                minGini = gini;
                bestSplit = split;
            }
        }

        Predicate leftPredicate = mapSplitToLeftPredicate(bestSplit);
        Predicate rightPredicate = mapSplitToRightPredicate(bestSplit);

        List<Boolean> leftDataIndices = dataInLeftNode(bestSplit, data);
        List<Boolean> rightDataIndices = dataInRightNode(bestSplit, data);

        BinaryNode leftChild = new BinaryNode(leftPredicate, data, leftDataIndices);
        BinaryNode rightChild = new BinaryNode(rightPredicate, data, rightDataIndices);

        List<BinaryNode> childNodes = new ArrayList<BinaryNode>();
        childNodes.add(leftChild);
        childNodes.add(rightChild);

        return childNodes;
    }

    private List<Boolean> dataInRightNode(CategoryBinarySplit bestSplit, List<FieldData> data) {
        // TODO Can this be optimized?
        return null;
    }

    private List<Boolean> dataInLeftNode(CategoryBinarySplit bestSplit, List<FieldData> data) {
        // TODO Can this be optimized?
        return null;
    }

    private Predicate mapSplitToRightPredicate(CategoryBinarySplit bestSplit) {
        // TODO Auto-generated method stub
        return null;
    }

    private Predicate mapSplitToLeftPredicate(CategoryBinarySplit bestSplit) {
        // TODO Auto-generated method stub
        return null;
    }

    private List<CategoryBinarySplit> findCateSplits(List<FieldData> data) {

        // TODO:
        // We should use binary map to indicate the split to be efficient

        return null;
    }
}
