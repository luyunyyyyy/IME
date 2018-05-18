package Util;

import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PinyinSplit {
    private static HashSet<String> hashSet;
    private static Logger logger = Logger.getLogger(PinyinSplit.class);

    public static String split(String input) {
        logger.debug("split 方法被调用");
        return input;
    }

    /*
    这个方法会在beijing 切分后变成 bei\sjing\s
    末尾有空格
     */
    public static String splitSpell(String s) {
        String regEx = "[^aoeiuv]?h?[iuv]?(ai|ei|ao|ou|er|ang?|eng?|ong|a|o|e|i|u|ng|n)?";
        int tag = 0;
        String spell = "";
        List<String> tokenResult = new LinkedList<String>();
        for (int i = s.length(); i > 0; i = i - tag) {
            Pattern pat = Pattern.compile(regEx);
            Matcher matcher = pat.matcher(s);
            matcher.find();
            spell += (matcher.group() + " ");
            tag = matcher.end() - matcher.start();
            tokenResult.add(s.substring(0, 1));
            s = s.substring(tag);
        }

        return spell;
    }

}
