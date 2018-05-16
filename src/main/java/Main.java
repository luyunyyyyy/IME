import org.apache.log4j.Logger;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    static Logger logger = Logger.getLogger(Main.class);
    public static void main(String[] args) {
        logger.info("调用 main ");
        Scanner sc = new Scanner(System.in);
        Pattern pinyinPattern = Pattern.compile("^[A-Za-z]+$");
        String input;
        while (true) {
            System.out.print("input:");
            input = sc.next();
            logger.debug("input : "+input);

            if(input.equals("q")){
                logger.debug("quit");
                break;
            }
            if(pinyinPattern.matcher(input).matches()){
                logger.info("input is pinyin");
            }
        }


        logger.info("输入法运行结束 ");

    }
}
