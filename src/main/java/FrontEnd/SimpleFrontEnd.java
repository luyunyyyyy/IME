package FrontEnd;


import BackEnd.BackEndService;
import IMEngine.IMEngineFactoryService;
import IMEngine.IMEngineInstance;
import Util.CandidateItem;
import Util.PinyinSplit;
import Util.SplitResultItem;
import org.apache.log4j.Logger;

import java.util.List;

/*
    下午要做的 TODO
    1. 如果FrontEnd作为一个调度器和组合的模块, 可以通过 BackEnd 来进行注入
 */
public class SimpleFrontEnd implements FrontEndService {
    private Logger logger = Logger.getLogger(SimpleFrontEnd.class);
    private BackEndService backEndService;
    private IMEngineInstance imEngineInstance;
    public SimpleFrontEnd(BackEndService backEndService) {
        logger.debug("SimpleFrontEnd 构造方法被调用");
        this.backEndService = backEndService;
        init();
    }

    @Override
    public void init() {
        logger.debug("SimpleFrontEnd init方法被调用");
        addIMEngineInstance();

    }

    /*
        此处需要一个数据对象来传递类型
     */
    @Override
    public String handleInputWords(String input) {
        logger.debug("SimpleFrontEnd handleInputWords 方法被调用");
        String result = PinyinSplit.splitSpell(input);
        List<String> candidate = imEngineInstance.getCandidateWord(result);

        return String.join("-", candidate);
    }

    /*
        TODO 需要一个预处理
     */
    @Override
    public List<CandidateItem> handleInputString(String input) {
        logger.debug("SimpleFrontEnd handleInputString 方法被调用 input : " + input);
//        String result = PinyinSplit.splitSpell(input);
//        List<String> candidate = imEngineInstance.getCandidateWord(result);
        List<SplitResultItem> simpleSplitResult = PinyinSplit.getSplitResultItems(input);
        List<CandidateItem> candidateItemList = imEngineInstance.getCandidateWords(simpleSplitResult);
        logger.debug("candidateItemList count : " + candidateItemList.size());
        return candidateItemList;
    }

    @Override
    public int addIMEngineInstance(String instanceUUID) {
        return 0;
    }

    @Override
    public int addIMEngineInstance() {
        IMEngineFactoryService imEngineFactoryService = backEndService.getIMEngineFactory();
        if (imEngineFactoryService == null) {
            logger.error("找不到对应的engine");
            return -1;
        }
        imEngineInstance = imEngineFactoryService.getIMEngineInstance();
        logger.debug("加载 IMEngineInstance 成功");
        return 0;
    }

    public static void main(String[] args) {
        System.out.println("SimplrFrontEnd");
    }
}
