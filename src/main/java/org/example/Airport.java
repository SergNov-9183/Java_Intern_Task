package org.example;

public class Airport {
    public boolean isValid;
    public Integer column1;
    public String column2;
    public String column3;
    public String column4;
    public String column5;
    public String column6;
    public Float column7;
    public Float column8;
    public Integer column9;
    public Float column10;
    public String column11;
    public String column12;
    public String column13;
    public String column14;

    private static boolean isN(String value) {
        return value.equalsIgnoreCase("\\N");
    }

    private static Integer toInteger(String value) {
        return isN(value) ? null : Integer.parseInt(value);
    }
    private static Float toFloat(String value) {
        return isN(value) ? null : Float.parseFloat(value);
    }

    private static String toString(String value) {
        Integer len = value.length();
        return isN(value) ? null : len < 3 ? "" : value.substring(1).substring(0, len - 2);
    }

    Airport(String string, boolean convert) {
        String[] splitValues = string.split(",");
        isValid = splitValues.length == 14;
        if (isValid) {
            column2 = toString(splitValues[1]);
            if (convert) {
                column1 = toInteger(splitValues[0]);
                column3 = toString(splitValues[2]);
                column4 = toString(splitValues[3]);
                column5 = toString(splitValues[4]);
                column6 = toString(splitValues[5]);
                column7 = toFloat(splitValues[6]);
                column8 = toFloat(splitValues[7]);
                column9 = toInteger(splitValues[8]);
                column10 = toFloat(splitValues[9]);
                column11 = toString(splitValues[10]);
                column12 = toString(splitValues[11]);
                column13 = toString(splitValues[12]);
                column14 = toString(splitValues[13]);
            }
        }
    }
}
