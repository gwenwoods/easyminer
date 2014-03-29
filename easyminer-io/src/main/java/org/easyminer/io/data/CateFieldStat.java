package org.easyminer.io.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class CateFieldStat {

    private final String fieldName;

    private List<String> levels = new ArrayList<String>();

    private Map<String, Integer> cateCountMap = new HashMap<String, Integer>();

    private final Map<String, Double> cateNorCountMap = new HashMap<String, Double>();

    private Double maxNormalizedCount;

    public CateFieldStat(String fieldName, List<String> levels, Map<String, Integer> cateCountMap) {
        this.fieldName = fieldName;
        this.levels = levels;
        this.cateCountMap = cateCountMap;

        Integer totalCount = 0;
        for (Integer count : cateCountMap.values()) {
            totalCount += count;
        }

        for (Entry<String, Integer> entry : cateCountMap.entrySet()) {
            String cate = entry.getKey();
            Double normalizedCount = entry.getValue().doubleValue() / totalCount.doubleValue();
            cateNorCountMap.put(cate, normalizedCount);
            System.out.println(cate + " " + normalizedCount);
        }
    }

    public String getFieldName() {
        return fieldName;
    }

    public List<String> getLevels() {
        return levels;
    }

    public Map<String, Integer> getCateCountMap() {
        return cateCountMap;
    }

    public Map<String, Double> getCateNorCountMap() {
        return cateNorCountMap;
    }

    public Double getMaxNormalizedCount() {
        if (maxNormalizedCount == null) {
            for (Entry<String, Double> entry : cateNorCountMap.entrySet()) {
                if (maxNormalizedCount == null || entry.getValue() > maxNormalizedCount) {
                    maxNormalizedCount = entry.getValue();
                }
            }
        }
        return maxNormalizedCount;
    }

}
