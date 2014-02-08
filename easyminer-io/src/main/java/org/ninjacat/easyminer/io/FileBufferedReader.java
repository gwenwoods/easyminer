package org.ninjacat.easyminer.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Read the csv file into a BufferReader.
 * 
 * @author wenlin
 */
public final class FileBufferedReader {

    public static BufferedReader read(String filename) {

        InputStream instream;
        try {
            instream = new FileInputStream(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
            return reader;
        } catch (FileNotFoundException e) {
            System.out.println(filename + " not found");
        }

        return null;
    }
}
