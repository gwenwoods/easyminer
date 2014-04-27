package org.easyminer.statistics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.easyminer.statistics.histogramold.DataRange;
import org.easyminer.statistics.histogramold.FileCleanser;
import org.easyminer.statistics.histogramold.HistogramGenerator;
import org.easyminer.statistics.histogramold.NumericHistogramGenerator;

public final class ComplainceEase2011 {

    private static final String PATH =
        "/Users/wenlin/eclipse_indigo/workspaces/analytics/analytics/src/tila/ca_2009/";

    private static final String CSV = ".csv";

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        String source = "CA_2009_rpt";

        String inputFileName = PATH + source + CSV;

        String cleanedFileName = PATH + source + "_cleaned" + CSV;

        FileCleanser fileCleanser = new FileCleanser(inputFileName, cleanedFileName);
        fileCleanser.clean();

        List<String> headerList = fileCleanser.getHeaderList();
        System.out.println("field number = " + headerList.size());
        System.out.println("header :  " + headerList.toString());
        System.out.println("categorical fields :  " + fileCleanser.getCateFieldList().toString());
        System.out.println("numerical fields :  " + fileCleanser.getNumericFieldList().toString());

        System.out.println();
        for (String fieldName : fileCleanser.getCateFieldList()) {
            System.out.println(fieldName + " :  " + fileCleanser.getCategoriesForField(fieldName));
        }

        // ---------------------------
        // pre-process data
        List<String> myCategoryFields = new ArrayList<String>();
        myCategoryFields.add("occupancy_type");
        myCategoryFields.add("program type");
        myCategoryFields.add("loan purpose");
        myCategoryFields.add("loan type");
        myCategoryFields.add("lien type");

        HistogramGenerator hg = new HistogramGenerator(cleanedFileName, source, PATH);
        for (String fieldName : myCategoryFields) {
            hg.createDualHistogramForField(fieldName, fileCleanser.getCategoriesForField(fieldName), "TILA",
                "pass", "fail");
        }

        NumericHistogramGenerator nhg = new NumericHistogramGenerator(cleanedFileName, source, PATH);
        List<String> myNumericFields = new ArrayList<String>();
        myNumericFields.add("Disclosed APR");
        myNumericFields.add("LTV");
        myNumericFields.add("CLTV");
        myNumericFields.add("interest_rate");
        // myNumericFields.add("disclosed_fin_charge");
        // myNumericFields.add("loan amt");
        myNumericFields.add("ARM");
        myNumericFields.add("maturity_term");

        for (String fieldName : myNumericFields) {
            nhg.createDualHistogramForField(fieldName, fileCleanser.getFieldDataRangeMap().get(fieldName),
                "TILA", "pass", "fail");
        }

        DataRange range = new DataRange(0, 1000000);
        nhg.createDualHistogramForField("loan amt", range, "TILA", "pass", "fail");

        nhg.createDualHistogramForField("disclosed_fin_charge", range, "TILA", "pass", "fail");

        // no histogram: zip, units_in_ppty, closing/app_received_by_creditor
    }
}
