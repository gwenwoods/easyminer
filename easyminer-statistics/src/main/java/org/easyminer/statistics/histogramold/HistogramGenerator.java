package org.easyminer.statistics.histogramold;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;

public final class HistogramGenerator {

    private final String inputFileName;

    private final String source;

    private final String histoDir;

    public HistogramGenerator(String inputFileName, String source, String histoDir) {
        super();
        this.inputFileName = inputFileName;
        this.source = source;
        this.histoDir = histoDir;
    }

    public void createDualHistogramForField(String fieldName, List<String> categories, String targetName,
        String cateGood, String cateBad) throws IOException {

        List<HistoBar> goodHistoList = new ArrayList<HistoBar>();
        List<HistoBar> badHistoList = new ArrayList<HistoBar>();
        List<HistoBar> goodHistoNorList = new ArrayList<HistoBar>();
        List<HistoBar> badHistoNorList = new ArrayList<HistoBar>();

        File inFile = new File(inputFileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inFile)));

        Map<String, Double> cateCountMap_good = new HashMap<String, Double>();
        Map<String, Double> cateCountMap_bad = new HashMap<String, Double>();
        for (String cate : categories) {
            cateCountMap_good.put(cate, Double.valueOf(0));
            cateCountMap_bad.put(cate, Double.valueOf(0));
        }

        String header = br.readLine();
        int fieldIndex = findIndex(header, fieldName);
        int targetIndex = findIndex(header, targetName);
        String inputLine;
        while ((inputLine = br.readLine()) != null) {
            String[] stringValues;
            stringValues = inputLine.split(",");

            String value = stringValues[fieldIndex];
            String target = stringValues[targetIndex];
            if (target.equals("pass")) {
                Double cateCount = cateCountMap_good.get(value);
                cateCount = cateCount + 1.0;
                cateCountMap_good.put(value, cateCount);
            } else if (target.equals("fail")) {
                Double cateCount = cateCountMap_bad.get(value);
                cateCount = cateCount + 1.0;
                cateCountMap_bad.put(value, cateCount);
            } else {
                System.out.println("invalid_target");
            }
        }

        System.out.println(cateCountMap_good.toString());
        System.out.println(cateCountMap_bad.toString());

        for (Entry<String, Double> entry : cateCountMap_good.entrySet()) {
            goodHistoList.add(new HistoBar(entry.getKey(), entry.getValue()));
            badHistoList.add(new HistoBar(entry.getKey(), cateCountMap_bad.get(entry.getKey())));
        }

        goodHistoNorList = histNorm(goodHistoList);
        badHistoNorList = histNorm(badHistoList);

        // ----------------------------
        JFrame frame = new JFrame(fieldName);
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DualHistogram his = new DualHistogram();
        his.setFieldName(fieldName);
        his.setHistoDir(histoDir);
        his.setSource(source);
        his.setHistoList(goodHistoNorList, badHistoNorList);
        frame.add(his);

        frame.setLocation(200, 200);
        frame.setVisible(true);
    }

    private int findIndex(String header, String fieldName) {
        int index = -1;
        String[] headerValues;
        headerValues = header.split(",");

        for (int i = 0; i < headerValues.length; i++) {
            if (fieldName.equals(headerValues[i])) {
                return i;
            }
        }

        return index;
    }

    private List<HistoBar> histNorm(List<HistoBar> histoList) {
        List<HistoBar> normHistList = new ArrayList<HistoBar>();

        double countSum = 0.0;
        for (HistoBar hb : histoList) {
            countSum += hb.getCount();
        }
        for (HistoBar hb : histoList) {
            normHistList.add(new HistoBar(hb.getName(), hb.getCount() / countSum));
        }
        return normHistList;
    }
}
