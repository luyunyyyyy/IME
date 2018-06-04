package Util;

import java.util.List;

public class StringUtil {
    public static String splitResultItemListToString(List<SplitResultItem> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (SplitResultItem item : list) {
            stringBuilder.append(item.getPyItem());
            stringBuilder.append("," + item.getPyType() + ";");
        }
        return stringBuilder.toString();
    }

}
