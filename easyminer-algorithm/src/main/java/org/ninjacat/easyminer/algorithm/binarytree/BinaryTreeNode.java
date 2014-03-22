package org.ninjacat.easyminer.algorithm.binarytree;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.dmg.pmml42.NodeDocument;
import org.ninjacat.easyminer.algorithm.tree.binary.IsInPredicate;
import org.ninjacat.easyminer.algorithm.tree.binary.Predicate;

public final class BinaryTreeNode {

    String score;

    int nodeId;

    Predicate predicate;

    boolean hasChildren;

    BinaryTreeNode leftChild, rightChild;

    /**
     * 
     * @param predicate
     * @param data
     *            all
     * @param target
     * @param usedDataIndex
     */
    public BinaryTreeNode(Predicate predicate, String[][] cateData, Double[][] numericData, String[] target,
        boolean[] nodeRecords, CateFieldInfo[] cateFieldsInfo) {
        this.predicate = predicate;

        // this.score = ((StringData) target).getWinningCate();
        List<BinaryTreeNode> childrenNodes =
            splitToChildren(cateData, numericData, target, nodeRecords, cateFieldsInfo);

        if (childrenNodes != null && childrenNodes.size() == 2) {
            this.leftChild = childrenNodes.get(0);
            this.rightChild = childrenNodes.get(1);
            this.hasChildren = true;
        }

        System.out.println("create a node " + predicate.getClass().getSimpleName() + " " + this.score);
        if (predicate instanceof IsInPredicate) {
            // System.out.println("  " + ((IsInPredicate) predicate).values);
        }

    }

    private List<BinaryTreeNode> splitToChildren(String[][] cateData, Double[][] numericData,
        String[] target, boolean[] nodeRecords, CateFieldInfo[] cateFieldsInfo) {

        // if (((StringData) target).getData().size() < 5) {
        // return null;
        // }

        Split bestSplit = findBestSplit(cateData, numericData, target, nodeRecords, cateFieldsInfo);

        // List<Boolean> leftDataIndices = dataInLeftNode(bestSplit, data);
        // List<Boolean> rightDataIndices = dataInRightNode(bestSplit, data);

        // ------------------------------------------------------
        // Split data to child nodes

        // List<FieldData> leftChildData = new ArrayList<FieldData>();
        // List<FieldData> rightChildData = new ArrayList<FieldData>();

        String splitFieldName = bestSplit.getSplitField();
        // FieldData splitData = null;
        //
        // for (FieldData fieldData : data) {
        // if (fieldData.getFieldName().equals(splitFieldName)) {
        // splitData = fieldData;
        // break;
        // }
        // }
        //
        // Set<String> leftCate = bestSplit.getLeftCateSet(((StringData) splitData).getCateLevels());
        //
        // for (FieldData fieldData : data) {
        // FieldData leftData = new StringData(fieldData.getFieldName());
        // FieldData rightData = new StringData(fieldData.getFieldName());
        //
        // leftChildData.add(leftData);
        // rightChildData.add(rightData);
        // }
        //
        // FieldData leftChildTarget = new StringData(target.getFieldName());
        // FieldData rightChildTarget = new StringData(target.getFieldName());
        //
        // for (int recordIndex = 0; recordIndex < ((StringData) splitData).getData().size(); recordIndex++) {
        // String record = (String) splitData.getRecord(recordIndex);
        // String recordTarget = (String) target.getRecord(recordIndex);
        // if (leftCate.contains(record)) {
        // for (int columnIndex = 0; columnIndex < data.size(); columnIndex++) {
        // String value = (String) data.get(columnIndex).getRecord(recordIndex);
        // leftChildData.get(columnIndex).addRecord(value);
        // }
        // leftChildTarget.addRecord(recordTarget);
        //
        // } else {
        // for (int columnIndex = 0; columnIndex < data.size(); columnIndex++) {
        // String value = (String) data.get(columnIndex).getRecord(recordIndex);
        // rightChildData.get(columnIndex).addRecord(value);
        // }
        // rightChildTarget.addRecord(recordTarget);
        // }
        // }

        // ---------------------------------------------------

        Predicate leftPredicate = null;
        Predicate rightPredicate = null;

        boolean[] leftNodeRecords = new boolean[target.length];
        boolean[] rightNodeRecords = new boolean[target.length];

        if (bestSplit instanceof CateSplit) {
            Set<String> leftCateSet = ((CateSplit) bestSplit).getLeftCateSet();
            Set<String> rightCateSet = ((CateSplit) bestSplit).getRightCateSet();

            leftPredicate = new IsInPredicate(splitFieldName, leftCateSet);
            rightPredicate = new IsInPredicate(splitFieldName, rightCateSet);

            int splitFieldIndex = findSplitFieldIndex(cateFieldsInfo);
            String[] fieldColumnData = new String[target.length];
            for (int index = 0; index < target.length; index++) {
                fieldColumnData[index] = cateData[index][splitFieldIndex];
            }

            for (int i = 0; i < target.length; i++) {
                if (nodeRecords[i]) {
                    if (leftCateSet.contains(fieldColumnData[i])) {
                        leftNodeRecords[i] = true;
                    } else {
                        rightNodeRecords[i] = true;
                    }
                }
            }
        } else {
            ((NumSplit) bestSplit).g
        }
        BinaryTreeNode leftChild =
            new BinaryTreeNode(leftPredicate, cateData, numericData, target, leftNodeRecords, cateFieldsInfo);
        BinaryTreeNode rightChild =
            new BinaryTreeNode(rightPredicate, cateData, numericData, target, rightNodeRecords,
                cateFieldsInfo);

        List<BinaryTreeNode> childNodes = new ArrayList<BinaryTreeNode>();
        childNodes.add(leftChild);
        childNodes.add(rightChild);

        return childNodes;
    }

    private int findSplitFieldIndex(CateFieldInfo[] cateFieldsInfo) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * 
     * @param data
     *            all input fields, no target field
     * @param target
     *            the target field data
     * @return the best split
     */
    private Split findBestSplit(String[][] cateData, Double[][] numericData, String[] target,
        boolean[] nodeRecords, CateFieldInfo[] cateFieldsInfo) {

        Split bestSplit = null;
        Double minimumImpurity = null;

        // -------------------------------------------
        // Check all cate splits
        int cateFieldNum = cateData[0].length;
        for (int i = 0; i < cateFieldNum; i++) {
            List<String> cateLevels = cateFieldsInfo[i].getLevels();
            String fieldName = cateFieldsInfo[i].getFieldName();

            String[] fieldColumnData = new String[target.length];
            for (int index = 0; index < target.length; index++) {
                fieldColumnData[index] = cateData[index][i];
            }

            int splitsCount = (int) Math.pow(2, cateLevels.size());
            for (int splitIndex = 0; i < splitsCount; i++) {
                CateSplit split = new CateSplit(fieldName, splitIndex, cateLevels);
                Double splitImpurity = split.childImpurity(fieldColumnData, target, cateLevels, nodeRecords);
                if (minimumImpurity == null || splitImpurity < minimumImpurity) {
                    minimumImpurity = splitImpurity;
                    bestSplit = split;
                }
            }
        }

        // --------------------------------
        // Chek Num Split
        int numericFieldNum = numericData[0].length;
        for (int i = 0; i < numericFieldNum; i++) {
            List<String> cateLevels = cateFieldsInfo[i].getLevels();
            String fieldName = cateFieldsInfo[i].getFieldName();

            String[] fieldColumnData = new String[target.length];
            for (int index = 0; index < target.length; index++) {
                fieldColumnData[index] = cateData[index][i];
            }

            int splitsCount = (int) Math.pow(2, cateLevels.size());
            for (int splitIndex = 0; i < splitsCount; i++) {
                CateSplit split = new CateSplit(fieldName, splitIndex, cateLevels);
                Double splitImpurity = split.childImpurity(fieldColumnData, target, cateLevels, nodeRecords);
                if (minimumImpurity == null || splitImpurity < minimumImpurity) {
                    minimumImpurity = splitImpurity;
                    bestSplit = split;
                }
            }
        }

        // for (FieldData fieldData : data) {
        // if (fieldData instanceof StringData) {
        //
        // String fieldName = fieldData.getFieldName();
        // List<String> levels = ((StringData) fieldData).getCateLevels();
        //
        // int splitsCount = (int) Math.pow(2, levels.size());
        // for (int i = 0; i < splitsCount; i++) {
        // CateBinarySplit split = new CateBinarySplit(fieldName, i);
        // Double splitImpurity = split.childImpurity((StringData) fieldData, (StringData) target);
        // if (minimumImpurity == null || splitImpurity < minimumImpurity) {
        // minimumImpurity = splitImpurity;
        // bestSplit = split;
        // }
        // }
        // }
        // }

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
