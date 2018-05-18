package Util;

import org.apache.log4j.Logger;

import java.util.HashSet;

public class PinyinSplit {
    private static HashSet<String> hashSet;
    private static Logger logger = Logger.getLogger(PinyinSplit.class);

    public static String split(String input) {
        logger.debug("split 方法被调用");
        return input;
    }
}
