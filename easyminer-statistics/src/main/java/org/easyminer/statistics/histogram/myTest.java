package org.easyminer.statistics.histogram;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.easyminer.io.CSVReader;
import org.easyminer.io.data.CateFieldStat;
import org.easyminer.io.data.Data;

public class myTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        String fieldName = "woof";
        JFrame frame = new JFrame(fieldName);
        frame.setSize(1024, 768);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //
        // FieldCateDataStat dataA = new FieldCateDataStat();
        //
        // List<String> myLevels = new ArrayList<String>();
        // myLevels.add("Dog");
        // myLevels.add("Cat");
        // myLevels.add("Cow");
        // dataA.setLevels(myLevels);
        // Map<String, Double> cateNorCountMap = new HashMap<String, Double>();
        // cateNorCountMap.put("Dog", 0.25);
        // cateNorCountMap.put("Cat", 0.35);
        // cateNorCountMap.put("Cow", 0.4);
        // dataA.setCateNorCountMap(cateNorCountMap);
        //
        // FieldCateDataStat dataB = new FieldCateDataStat();
        // List<String> myLevelsB = new ArrayList<String>();
        // myLevelsB.add("Dog");
        // myLevelsB.add("Cat");
        // myLevelsB.add("Cow");
        // myLevelsB.add("Bird");
        // dataB.setLevels(myLevelsB);
        // Map<String, Double> cateNorCountMapB = new HashMap<String, Double>();
        // cateNorCountMapB.put("Dog", 0.45);
        // cateNorCountMapB.put("Cat", 0.2);
        // cateNorCountMapB.put("Cow", 0.3);
        // cateNorCountMapB.put("Bird", 0.15);
        // dataB.setCateNorCountMap(cateNorCountMapB);
        //
        // FieldCateDataStat dataC = new FieldCateDataStat();
        // List<String> myLevelsC = new ArrayList<String>();
        // myLevelsC.add("Dog");
        // myLevelsC.add("Cat");
        // myLevelsC.add("Cow");
        // myLevelsC.add("Bird");
        // dataC.setLevels(myLevelsC);
        // Map<String, Double> cateNorCountMapC = new HashMap<String, Double>();
        // cateNorCountMapC.put("Dog", 0.25);
        // cateNorCountMapC.put("Cat", 0.2);
        // cateNorCountMapC.put("Cow", 0.45);
        // cateNorCountMapC.put("Bird", 0.1);
        // dataC.setCateNorCountMap(cateNorCountMapC);
        //
        List<CateFieldStat> fieldDataList = new ArrayList<CateFieldStat>();
        // fieldDataList.add(dataA);
        // fieldDataList.add(dataB);
        // fieldDataList.add(dataC);

        Data data = CSVReader.read("/Users/wenlin/Desktop/audit.csv");
        data.getCateHeader();
        CateFieldStat employmentStat = data.getCateFieldStat("Employment");
        CateFieldStat educationStat = data.getCateFieldStat("Education");
        // fieldDataList.add(employmentStat);
        fieldDataList.add(educationStat);
        Histogram his = new Histogram(fieldDataList);

        // his.setFieldName(fieldName);
        // his.setHistoDir(histoDir);
        // his.setSource(source);
        // his.setHistoList(goodHistoNorList, badHistoNorList);
        frame.add(his);

        frame.setLocation(200, 200);
        frame.setVisible(true);

    }
}
