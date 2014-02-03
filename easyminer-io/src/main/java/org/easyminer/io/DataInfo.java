package org.easyminer.io;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The class which hold the information of an input csv file.
 */
public final class DataInfo {

    String baseFilename;
    List<String> headFields = new ArrayList<String>();

    DataInfo(String baseFilename) {
        super();
        this.baseFilename = baseFilename;
    }

    public void addHeaderField(String fieldName) {
        headFields.add(fieldName);
    }

    public List<String> getHeaderFields() {
        return Collections.unmodifiableList(headFields);
    }
}
