package org.ninjacat.easyminer.io.data;

/**
 * The data.
 */
public final class Data {

    String[][] data;
    String[] header;

    String[] columnType;

    String[][] cateData;
    Double[][] numericData;

    String[] cateHeader;
    String[] numericHeader;

    int cateFieldNum;
    int numericFieldNum;

    // String baseFilename;
    // List<String> headFields = new ArrayList<String>();

    public Data(String[][] data, String[] header) {

        int rowNum = data.length;
        int columnNum = data[0].length;

        columnType = new String[columnNum];
        numericFieldNum = columnNum;

        for (int j = 0; j < columnNum; j++) {
            columnType[j] = "Double";

            for (int i = 0; i < rowNum; i++) {

                if (data[i][j] != null) {
                    try {
                        Double.parseDouble(data[i][j]);
                    } catch (NumberFormatException e) {
                        columnType[j] = "String";
                        cateFieldNum++;
                        numericFieldNum--;
                        break;
                    }
                }
            }
        }

        cateData = new String[rowNum][cateFieldNum];
        numericData = new Double[rowNum][numericFieldNum];

        cateHeader = new String[cateFieldNum];
        numericHeader = new String[numericFieldNum];

        int cateIndex = 0;
        int numericIndex = 0;

        // System.out.println("meow " + cateData[1][1]);

        for (int j = 0; j < columnNum; j++) {

            if ("Double".equals(columnType[j])) {
                for (int i = 0; i < rowNum; i++) {

                    if (data[i][j] != null) {
                        try {
                            Double value = Double.parseDouble(data[i][j]);
                            numericData[i][numericIndex] = value;
                            numericHeader[numericIndex] = header[j];
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            // should not happen here
                        }
                    }
                }
                numericIndex++;
            } else {
                for (int i = 0; i < rowNum; i++) {

                    if (data[i][j] != null) {
                        cateData[i][cateIndex] = data[i][j];
                        cateHeader[cateIndex] = header[j];
                    }
                }
                cateIndex++;
            }

        }

    }

    public String[] getCateHeader() {
        return cateHeader;
    }

    public String[] getNumericHeader() {
        return numericHeader;
    }

    public String[][] getCateData() {
        return cateData;
    }

    public Double[][] getNumericData() {
        return numericData;
    }

    // public void addHeaderField(String fieldName) {
    // headFields.add(fieldName);
    // }
    //
    // public List<String> getHeaderFields() {
    // return Collections.unmodifiableList(headFields);
    // }

    public String[] getColumnType() {
        return columnType;
    }

}
