package org.easyminer.statistics.histogramold;

public final class HistoBar {

    private final String name;
    private final Double count;

    public final String getName() {
        return name;
    }

    public final Double getCount() {
        return count;
    }

    public HistoBar(String name, Double count) {
        this.name = name;
        this.count = count;
    }
}
