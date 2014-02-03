package org.easyminer.io;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Hello world!
 * 
 */
public class App {
    public static void main(String[] args) {

        try {

            BufferedReader csvBufferedReader = CSVReader.read("./src/main/resources/audit_nor.csv");

            String header = csvBufferedReader.readLine();
            String[] headerArray = header.split(",");

            // Always close files.

            Runtime rt = Runtime.getRuntime();
            // Process pr = rt.exec("R CMD BATCH lmExtractor.R");
            rt.exec("mkdir dataDir");

            OutputStream[] fieldDataStreamArray = new OutputStream[headerArray.length];
            for (int i = 0; i < headerArray.length; i++) {
                // for (String fieldName : headerArray) {
                // System.out.println(fieldName);
                String fieldName = headerArray[i];
                fieldDataStreamArray[i] =
                    new BufferedOutputStream(new FileOutputStream("dataDir/" + fieldName + ".csv"));

            }

            // InputStream newStr = new ByteArrayInputStream(fieldName.getBytes());

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
                        // if (fieldDataStreamArray[i] != null) {
                        fieldDataStreamArray[i].write(buffer, 0, len);
                        // }
                    }
                }
            }

            for (int i = 0; i < headerArray.length; i++) {
                if (fieldDataStreamArray[i] != null)
                    fieldDataStreamArray[i].close();
            }

            csvBufferedReader.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // FileOutputStream fos = new FileOutputStream("pathname");
        // fos.write(myByteArray);
        // fos.close();
    }
}
