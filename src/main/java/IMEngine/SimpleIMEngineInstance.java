package IMEngine;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
    这是一个返回拼音切分结果的引擎.
 */
public class SimpleIMEngineInstance implements IMEngineInstance {
    Logger logger = Logger.getLogger(SimpleIMEngineInstance.class);
    @Override
    public List<String> getCandidateWord(String pySeries) {
        logger.debug("调用 getCandidateWord 获得拼音切分序列");
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(pySeries.split(" ")));
        return arrayList;
    }
}
