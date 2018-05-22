package Util;

import Config.SimpleConfig;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
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

    private static String parseTheChineseByObject(List<Map<String, Integer>> list) {
        Map<String, Integer> first = null; // 用于统计每一次,集合组合数据
        // 遍历每一组集合
        for (int i = 0; i < list.size(); i++) {
            // 每一组集合与上一次组合的Map
            Map<String, Integer> temp = new Hashtable<String, Integer>();
            // 第一次循环，first为空
            if (first != null) {
                // 取出上次组合与此次集合的字符，并保存
                for (String s : first.keySet()) {
                    for (String s1 : list.get(i).keySet()) {
                        String str = s + s1;
                        temp.put(str, 1);
                    }
                }
                // 清理上一次组合数据
                if (temp != null && temp.size() > 0) {
                    first.clear();
                }
            } else {
                for (String s : list.get(i).keySet()) {
                    String str = s;
                    temp.put(str, 1);
                }
            }
            // 保存组合数据以便下次循环使用
            if (temp != null && temp.size() > 0) {
                first = temp;
            }
        }
        String returnStr = "";
        if (first != null) {
            // 遍历取出组合字符串
            for (String str : first.keySet()) {
                returnStr += (str + ",");
            }
        }
        if (returnStr.length() > 0) {
            returnStr = returnStr.substring(0, returnStr.length() - 1);
        }
        return returnStr;
    }

    private static List<Map<String, Integer>> discountTheChinese(String theStr) {
        // 去除重复拼音后的拼音列表
        List<Map<String, Integer>> mapList = new ArrayList<Map<String, Integer>>();
        // 用于处理每个字的多音字，去掉重复
        Map<String, Integer> onlyOne = null;
        String[] firsts = theStr.split(" ");
        // 读出每个汉字的拼音
        for (String str : firsts) {
            onlyOne = new Hashtable<String, Integer>();
            String[] china = str.split(",");
            // 多音字处理
            for (String s : china) {
                Integer count = onlyOne.get(s);
                if (count == null) {
                    onlyOne.put(s, new Integer(1));
                } else {
                    onlyOne.remove(s);
                    count++;
                    onlyOne.put(s, count);
                }
            }
            mapList.add(onlyOne);
        }
        return mapList;
    }

    public static String[] converterToFirstSpell(String chines) {
        StringBuffer pinyinName = new StringBuffer();
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    // 取得当前汉字的所有全拼
                    String[] strs = PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat);
                    if (strs != null) {
                        for (int j = 0; j < strs.length; j++) {
                            // 取首字母
                            pinyinName.append(strs[j].charAt(0));
                            if (j != strs.length - 1) {
                                pinyinName.append(",");
                            }
                        }
                    }
                    // else {
                    // pinyinName.append(nameChar[i]);
                    // }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName.append(nameChar[i]);
            }
            pinyinName.append(" ");
        }
        // return pinyinName.toString();
        return parseTheChineseByObject(discountTheChinese(pinyinName.toString())).split(",");
    }

    private void init() {

    }

}
