package org.ninjacat.easyminer.algorithm.tree.binary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dmg.pmml42.NodeDocument;
import org.easyminer.io.FieldData;
import org.easyminer.io.StringData;

public final class BinaryNode {

    String score;

    int nodeId;

    Predicate predicate;

    boolean hasChildren;

    BinaryNode leftChild, rightChild;

    /**
     * 
     * @param predicate
     * @param data
     *            all
     * @param target
     * @param usedDataIndex
     */
    public BinaryNode(Predicate predicate, List<FieldData> data, FieldData target) {
        this.predicate = predicate;

        this.score = ((StringData) target).getWinningCate();
        List<BinaryNode> childrenNodes = splitToChildren(data, target);

        if (childrenNodes != null && childrenNodes.size() == 2) {
            this.leftChild = childrenNodes.get(0);
            this.rightChild = childrenNodes.get(1);
            this.hasChildren = true;
        }

        System.out.println("create a node " + predicate.getClass().getSimpleName() + " " + this.score);
        if (predicate instanceof IsInPredicate) {
            System.out.println("  " + ((IsInPredicate) predicate).values);
        }

    }

    private List<BinaryNode> splitToChildren(List<FieldData> data, FieldData target) {

        if (((StringData) target).getData().size() < 5) {
            return null;
        }

        CateBinarySplit bestSplit = findBestSplit(data, target);

        // List<Boolean> leftDataIndices = dataInLeftNode(bestSplit, data);
        // List<Boolean> rightDataIndices = dataInRightNode(bestSplit, data);

        // ------------------------------------------------------
        // Split data to child nodes
        List<FieldData> leftChildData = new ArrayList<FieldData>();
        List<FieldData> rightChildData = new ArrayList<FieldData>();

        String splitFieldName = bestSplit.getSplitField();
        FieldData splitData = null;

        for (FieldData fieldData : data) {
            if (fieldData.getFieldName().equals(splitFieldName)) {
                splitData = fieldData;
                break;
            }
        }

        Set<String> leftCate = bestSplit.getLeftCateSet(((StringData) splitData).getCateLevels());

        for (FieldData fieldData : data) {
            FieldData leftData = new StringData(fieldData.getFieldName());
            FieldData rightData = new StringData(fieldData.getFieldName());

            leftChildData.add(leftData);
            rightChildData.add(rightData);
        }

        FieldData leftChildTarget = new StringData(target.getFieldName());
        FieldData rightChildTarget = new StringData(target.getFieldName());

        for (int recordIndex = 0; recordIndex < ((StringData) splitData).getData().size(); recordIndex++) {
            String record = (String) splitData.getRecord(recordIndex);
            String recordTarget = (String) target.getRecord(recordIndex);
            if (leftCate.contains(record)) {
                for (int columnIndex = 0; columnIndex < data.size(); columnIndex++) {
                    String value = (String) data.get(columnIndex).getRecord(recordIndex);
                    leftChildData.get(columnIndex).addRecord(value);
                }
                leftChildTarget.addRecord(recordTarget);

            } else {
                for (int columnIndex = 0; columnIndex < data.size(); columnIndex++) {
                    String value = (String) data.get(columnIndex).getRecord(recordIndex);
                    rightChildData.get(columnIndex).addRecord(value);
                }
                rightChildTarget.addRecord(recordTarget);
            }
        }

        // ---------------------------------------------------
        Set<String> leftCateSet = bestSplit.getLeftCateSet(((StringData) splitData).getCateLevels());
        Set<String> rightCateSet = new HashSet<String>(((StringData) splitData).getCateLevels());
        rightCateSet.removeAll(leftCateSet);

        Predicate leftPredicate = new IsInPredicate(splitFieldName, leftCateSet);
        Predicate rightPredicate = new IsInPredicate(splitFieldName, rightCateSet);

        BinaryNode leftChild = new BinaryNode(leftPredicate, leftChildData, leftChildTarget);
        BinaryNode rightChild = new BinaryNode(rightPredicate, rightChildData, rightChildTarget);

        List<BinaryNode> childNodes = new ArrayList<BinaryNode>();
        childNodes.add(leftChild);
        childNodes.add(rightChild);

        return childNodes;
    }

    /**
     * 
     * @param data
     *            all input fields, no target field
     * @param target
     *            the target field data
     * @return the best split
     */
    private CateBinarySplit findBestSplit(List<FieldData> data, FieldData target) {

        CateBinarySplit bestSplit = null;
        Double minimumImpurity = null;

        for (FieldData fieldData : data) {
            if (fieldData instanceof StringData) {

                String fieldName = fieldData.getFieldName();
                List<String> levels = ((StringData) fieldData).getCateLevels();

                int splitsCount = (int) Math.pow(2, levels.size());
                for (int i = 0; i < splitsCount; i++) {
                    CateBinarySplit split = new CateBinarySplit(fieldName, i);
                    Double splitImpurity = split.childImpurity((StringData) fieldData, (StringData) target);
                    if (minimumImpurity == null || splitImpurity < minimumImpurity) {
                        minimumImpurity = splitImpurity;
                        bestSplit = split;
                    }
                }
            }
        }

        return bestSplit;
    }

    public NodeDocument.Node exportPMML() {

        // NodeDocument nodeDoc = NodeDocument.Factory.newInstance();
        NodeDocument.Node xmlNode = NodeDocument.Node.Factory.newInstance();
        xmlNode.setScore(score);

        if (leftChild != null && rightChild != null) {
            NodeDocument.Node xmlLeftChildNode = leftChild.exportPMML();
            NodeDocument.Node xmlRightchildNode = rightChild.exportPMML();
            xmlNode.addNewNode();
            xmlNode.addNewNode();

            xmlNode.setNodeArray(0, xmlLeftChildNode);
            xmlNode.setNodeArray(1, xmlRightchildNode);
        }

        return xmlNode;

    }
}
