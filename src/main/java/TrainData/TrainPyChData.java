package TrainData;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import static Config.TrainConfig.CORPUS_DIR_NAME;
import static Config.TrainConfig.TRAIN_DATA_INPUT_ROOT_PATH;

public class TrainPyChData {
    public static ArrayList<String> corpus;
    private static Logger logger = Logger.getLogger(TrainPyChData.class);

    static {
        corpus = new ArrayList<>();
    }

    public TrainPyChData() {

    }

    public static void main(String[] args) throws IOException, PinyinException {
//        BufferedReader bufferedReader;
//        HashMap<String, ArrayList<Pair<String, Double>>> simpleSpellDict;
//        try {
//            bufferedReader = new BufferedReader(new FileReader(TRAIN_DATA_INPUT_ROOT_PATH
//                    + CORPUS_DIR_NAME + ));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        HashMap<String, HashMap<String, Double>> py2ch = new HashMap<>();
        HashMap<String, Double> PI = new HashMap<>();
        HashMap<String, HashMap<String, Double>> emit = new HashMap<>();
        HashMap<String, HashMap<String, Double>> trans = new HashMap<>();
        readfile(TRAIN_DATA_INPUT_ROOT_PATH
                + CORPUS_DIR_NAME);
        logger.info(corpus.size());
        for (String sentence : corpus) {
            logger.debug(PinyinHelper.convertToPinyinString(sentence, ",", PinyinFormat.WITHOUT_TONE));
            String[] pyList = PinyinHelper.convertToPinyinString(sentence, ",", PinyinFormat.WITHOUT_TONE).split(",");

            if (PI.containsKey(pyList[0])) {
                PI.put(pyList[0], PI.get(pyList[0]) + 1);
            } else {
                PI.put(pyList[0], 1.0);
            }
        }

    }


    public static boolean readfile(String filepath) throws FileNotFoundException, IOException {
        try {

            File file = new File(filepath);
            if (!file.isDirectory()) {
                System.out.println("文件");
                System.out.println("path=" + file.getPath());
                System.out.println("absolutepath=" + file.getAbsolutePath());
                System.out.println("name=" + file.getName());

            } else if (file.isDirectory()) {
                System.out.println("文件夹");
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) {
                        System.out.println("path=" + readfile.getPath());
                        System.out.println("absolutepath="
                                + readfile.getAbsolutePath());
                        System.out.println("name=" + readfile.getName());
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(readfile));
                        String tmpStr;
                        while ((tmpStr = bufferedReader.readLine()) != null) {
                            corpus.add(tmpStr);
                        }
                    } else if (readfile.isDirectory()) {
                        readfile(filepath + "\\" + filelist[i]);
                    }
                }

            }

        } catch (FileNotFoundException e) {
            System.out.println("readfile()   Exception:" + e.getMessage());
        }
        return true;
    }

}
