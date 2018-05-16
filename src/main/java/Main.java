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
        while (true) {
            System.out.print("input:");
            input = sc.next();
            logger.debug("input : " + input);

            if (input.equals("q")) {
                logger.debug("quit");
                break;
            }
            if (pinyinPattern.matcher(input).matches()) {
                logger.debug("input is pinyin");

                result = setResult(input);
            } else {
                logger.debug("input is not pinyin");
                break;
            }

            if (result != null) {
                System.out.println("result : " + result);
            }
        }
    }

    private static String setResult(String input) {
        return input;
    }
}
