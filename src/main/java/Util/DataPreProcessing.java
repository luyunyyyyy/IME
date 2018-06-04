package Util;

public class DataPreProcessing {
    public static String preProcessing(String str) {
        str = str.toLowerCase();
        str = str.replaceAll("[^a-z]*", "");
        return str;
    }
}
