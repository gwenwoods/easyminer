package org.easyminer.io;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Split the csv data into small chunks based on field.
 * 
 */
public class CSVDataSplitter {

    public static final DataInfo splitCSV(String filename) {

        try {

            // -----------------------------------------
            // create directory for data
            Runtime rt = Runtime.getRuntime();
            rt.exec("mkdir dataDir");

            DataInfo dataInfo = new DataInfo(filename);
            // -----------------------------------------
            BufferedReader csvBufferedReader = FileBufferedReader.read(filename);

            // -----------------------------------------
            // Read header and create dataStream and file for each field

            String header = csvBufferedReader.readLine();
            String[] headerArray = header.split(",");

            OutputStream[] fieldDataStreamArray = new OutputStream[headerArray.length];

            for (int i = 0; i < headerArray.length; i++) {
                String fieldName = headerArray[i];
                dataInfo.addHeaderField(fieldName);

                fieldDataStreamArray[i] =
                    new BufferedOutputStream(new FileOutputStream("dataDir/" + fieldName + ".csv"));
            }

            // -------------------------------------------
            // Process record line
            String recordLine = null;

            while ((recordLine = csvBufferedReader.readLine()) != null) {
                String[] fieldDataArray = recordLine.split(",");
                for (int i = 0; i < fieldDataArray.length; i++) {
                    fieldDataArray[i] += "\n";
                    InputStream fieldDataStream = new ByteArrayInputStream(fieldDataArray[i].getBytes());
                    int bufferSize = 1024;
                    byte[] buffer = new byte[bufferSize];
                    int len = 0;
                    while ((len = fieldDataStream.read(buffer)) != -1) {
                        fieldDataStreamArray[i].write(buffer, 0, len);
                    }
                }
            }

            for (int i = 0; i < headerArray.length; i++) {
                if (fieldDataStreamArray[i] != null)
                    fieldDataStreamArray[i].close();
            }

            csvBufferedReader.close();

        } catch (IOException e) {

            System.out.println("Couldn't chop the input data to small chunks.");
            e.printStackTrace();
        }

        return null;
    }
}
