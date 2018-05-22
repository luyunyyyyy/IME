package FrontEnd;

import Util.CandidateItem;

import java.util.List;

public interface FrontEndService {
    void init();

    /*
        TODO 此处的接口需要更改, 这个返回值不能保存候选词的概率或者评分.
        此处需要一个数组, 数组内容是一个自定义对象.
     */
    @Deprecated
    String handleInputWords(String input);

    List<CandidateItem> handleInputString(String input);

    int addIMEngineInstance(String instanceUUID);

    int addIMEngineInstance();
}
