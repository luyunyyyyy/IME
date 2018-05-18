import BackEnd.BackEndService;
import BackEnd.SimpleBackEnd;
import FrontEnd.FrontEndService;
import FrontEnd.SimpleFrontEnd;
import IMEngine.IMEngineFactoryService;
import IMEngine.SimpleIMEngineFactory;
import org.apache.log4j.Logger;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static Logger logger = Logger.getLogger(Main.class);
    public static void main(String[] args) {
        logger.info("调用 main ");
        Scanner sc = new Scanner(System.in);
        runFrontEnd(sc);


        logger.info("输入法运行结束 ");

    }

    private static void runFrontEnd(Scanner sc) {
        Pattern pinyinPattern = Pattern.compile("^[A-Za-z]+$");
        String input, result;
        FrontEndService frontEndService = null;
        while (true) {
            System.out.print("input:");
            input = sc.next();
            logger.debug("input : " + input);

            if (input.equals("q")) {
                logger.debug("quit");
                break;
            }
            /*
            模块的功能入口
            TODO 0516 下午的工作
            1. 要把 result 抽象成一个类，以便于容纳候选词的数组 还要重写一下toString方法。然后提供一个通过索引访问单词的方法
            2. 要考虑参数的配置问题 比如说候选词的大小
             */
            if (pinyinPattern.matcher(input).matches()) {
                logger.debug("input is pinyin");

                result = frontEndService.handleInputWords(input);
                //result = setResult(input);
            } else {
                logger.debug("input is not pinyin");
                break;
            }

            if (result != null) {
                System.out.println("result : " + result);
            }
        }
    }
    private static FrontEndService init(){
        IMEngineFactoryService imEngineFactoryService = new SimpleIMEngineFactory();
        BackEndService backEndService = new SimpleBackEnd(imEngineFactoryService);
        FrontEndService frontEndService = new SimpleFrontEnd(backEndService);
        return frontEndService;
    }
    private static String setResult(String input) {
        return input;
    }
}
