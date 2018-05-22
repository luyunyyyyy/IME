import BackEnd.BackEndService;
import BackEnd.SimpleBackEnd;
import FrontEnd.FrontEndService;
import FrontEnd.SimpleFrontEnd;
import IMEngine.IMEngineFactoryService;
import IMEngine.SimpleIMEngineFactory;
import Util.CandidateItem;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        logger.info("调用 main ");
        Scanner sc = new Scanner(System.in);
        runFrontEnd(sc);


        logger.info("输入法运行结束 ");

    }

    private static void runFrontEnd(Scanner sc) throws IOException, ClassNotFoundException {
        Pattern pinyinPattern = Pattern.compile("^[A-Za-z]+$");
        String input, result;
        FrontEndService frontEndService = init();
        List<CandidateItem> candidateWords;
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

                //result = frontEndService.handleInputWords(input);
                candidateWords = frontEndService.handleInputString(input);

            } else {
                logger.debug("input is not pinyin");
                break;
            }

            if (candidateWords != null && candidateWords.size() != 0) {
                System.out.print("result:");
//                for(CandidateItem candidateItem : candidateWords){
//                    System.out.print( + candidateItem.getCandidateWord());
//                }
                for (int i = 0; i < candidateWords.size(); i++) {
                    System.out.print(i + "." + candidateWords.get(i).getCandidateWord() + "\t");
                }
                System.out.println("");
            } else {
                logger.info("无结果 candidateWords == null is null");
                System.out.println("无结果");
            }
        }
    }

    private static FrontEndService init() throws IOException, ClassNotFoundException {
        IMEngineFactoryService imEngineFactoryService = new SimpleIMEngineFactory();
        BackEndService backEndService = new SimpleBackEnd(imEngineFactoryService);
        FrontEndService frontEndService = new SimpleFrontEnd(backEndService);
        return frontEndService;
    }
    private static String setResult(String input) {
        return input;
    }
}
