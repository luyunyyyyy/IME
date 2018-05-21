package Util;

import Config.SimpleConfig;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PinyinSplit {
    private static HashSet<String> hashSet;
    private static Logger logger = Logger.getLogger(PinyinSplit.class);

//    public static String split(String input) {
//        logger.debug("split 方法被调用");
//        return input;
//    }

    /*
    这个方法会在beijing 切分后变成 bei\sjing\s
    末尾有空格
     */
    /*
        TODO 此处应该使用一个数组, 保存的是切分结果和每个位置对应的类别

     */

    public static String splitSpell(String s) {
        String regEx = "[^aoeiuv]?h?[iuv]?(ai|ei|ao|ou|er|ang?|eng?|ong|a|o|e|i|u|ng|n)?";
        int tag;
        StringBuilder spell = new StringBuilder();
        LinkedList tokenResult = new LinkedList();
        for (int i = s.length(); i > 0; i = i - tag) {
            Pattern pat = Pattern.compile(regEx);
            Matcher matcher = pat.matcher(s);
            matcher.find();
            spell.append(matcher.group()).append(",");
            tag = matcher.end() - matcher.start();
            tokenResult.add(s.substring(0, 1));
            s = s.substring(tag);
        }
        return spell.toString();
    }

    public static List<SplitResultItem> getSplitResultItems(String input) {
        List<String> splitResult = new ArrayList<>(Arrays.asList(splitSpell(input).split(",")));
        List<SplitResultItem> splitResultItems = new LinkedList<>();
        Set<String> completeSpellSet = SimpleConfig.getCompleteSpellSet();
        Set<String> simpleSpellSet = SimpleConfig.getSimpleSpellSet();
        PinYinType type;
        for (String tmp : splitResult) {
            if (completeSpellSet.contains(tmp)) {
                type = PinYinType.COMPLETESPELL;
                //splitResultItems.add(new SplitResultItem(tmp, PinYinType.COMPLETESPELL));
            } else if (simpleSpellSet.contains(tmp)) {
                type = PinYinType.SIMPLESPELL;
                //splitResultItems.add(new SplitResultItem(tmp, PinYinType.SIMPLESPELL));
            } else {
                type = PinYinType.OTHERSPELL;
            }
            splitResultItems.add(new SplitResultItem(tmp, type));
        }
        return splitResultItems;
    }

    public static void main(String[] args) {
        System.out.println(splitSpell("beijdxue"));
        List<SplitResultItem> result = getSplitResultItems("bj");
        for (SplitResultItem tmp : result) {
            System.out.println("result - item:" + tmp.getPyItem() + ", type:" + tmp.getPyType());
        }
    }

    private void init() {

    }

}
