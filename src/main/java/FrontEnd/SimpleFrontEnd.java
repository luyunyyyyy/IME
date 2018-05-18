package FrontEnd;


import BackEnd.BackEndService;
import IMEngine.IMEngineFactoryService;
import IMEngine.IMEngineInstance;
import Util.PinyinSplit;
import org.apache.log4j.Logger;

public class SimpleFrontEnd implements FrontEndService {
    private Logger logger = Logger.getLogger(SimpleFrontEnd.class);
    private BackEndService backEndService;
    private IMEngineInstance imEngineInstance;

    public SimpleFrontEnd(BackEndService backEndService) {
        logger.debug("SimpleFrontEnd 构造方法被调用");
        this.backEndService = backEndService;
    }

    public void init() {
        logger.debug("SimpleFrontEnd init方法被调用");

    }

    public String handleInputWords(String input) {
        logger.debug("SimpleFrontEnd handleInputWords 方法被调用");
        String result = PinyinSplit.splitSpell(input);

        //imEngineFactoryService.
        return result;
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
