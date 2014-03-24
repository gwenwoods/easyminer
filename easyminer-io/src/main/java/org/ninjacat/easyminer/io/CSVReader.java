package org.ninjacat.easyminer.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Pattern;

import org.ninjacat.easyminer.io.data.Data;

/**
 * Read csv file.
 * 
 */
public class CSVReader {

    private static Pattern patternQuotValue = Pattern.compile("^\".*\"$");

    private static Pattern patternQuot = Pattern.compile("^\"|\"$");

    public static final Data read(String filename) {

        String[][] rawData = createData(filename);
        String[] headerArray = null;
        int column = rawData[0].length;

        try {

            // -----------------------------------------
            // create directory for data
            BufferedReader csvBufferedReader = FileBufferedReader.read(filename);

            // -----------------------------------------
            // Read header and create dataStream and file for each field

            String header = csvBufferedReader.readLine();

            String[] headerSplit = header.split(",");
            headerArray = new String[headerSplit.length];

            for (int i = 0; i < headerSplit.length; i++) {
                String headerValue = headerSplit[i];
                if (patternQuotValue.matcher(headerValue).find()) {
                    headerArray[i] = patternQuot.matcher(headerValue).replaceAll("");
                } else {
                    headerArray[i] = headerValue;
                }
            }

            // -------------------------------------------
            // Process record line
            String recordLine = null;

            int rowIndex = 0;

            while ((recordLine = csvBufferedReader.readLine()) != null) {
                String[] fieldDataArray = recordLine.split(",");
                for (int columnIndex = 0; columnIndex < column; columnIndex++) {
                    String value = fieldDataArray[columnIndex];
                    if (patternQuotValue.matcher(value).find()) {
                        rawData[rowIndex][columnIndex] = patternQuot.matcher(value).replaceAll("");
                    } else {
                        rawData[rowIndex][columnIndex] = value;
                    }
                }
                rowIndex++;
            }

            csvBufferedReader.close();

        } catch (IOException e) {

            System.out.println("Couldn't chop the input data to small chunks.");
            e.printStackTrace();
        }

        return new Data(rawData, headerArray);
    }

    private static String[][] createData(String filename) {

        Integer column = 0;
        Integer recordCount = 0;

        try {

            BufferedReader csvBufferedReader = FileBufferedReader.read(filename);

            String header = csvBufferedReader.readLine();
            String[] headerArray = header.split(",");

            column = headerArray.length;

            // -------------------------------------------
            // Process record line

            while (csvBufferedReader.readLine() != null) {
                recordCount++;
            }

            csvBufferedReader.close();

        } catch (IOException e) {

            System.out.println("Couldn't read the csv file " + filename);
            e.printStackTrace();
        }

        return new String[recordCount][column];
    }
}
