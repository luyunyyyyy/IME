package TrainData;

import Util.CandidateItem;
import Util.SplitResultItem;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static Config.TrainConfig.CORPUS_DIR_NAME;
import static Config.TrainConfig.TRAIN_DATA_INPUT_ROOT_PATH;
import static IMEngine.Algorithm.SimpleViterbi.topkPath;
import static Util.PinyinSplit.getSplitResultItems;

public class TrainPyChData {
    public static ArrayList<String> corpus;
    private static Logger logger = Logger.getLogger(TrainPyChData.class);

    static {
        corpus = new ArrayList<>();
    }

    public TrainPyChData() {

    }

    public static void main(String[] args) throws IOException, PinyinException {

        HashMap<String, HashMap<String, Double>> py2ch = new HashMap<>();
        HashMap<String, Double> PI = new HashMap<>();
        HashMap<String, HashMap<String, Double>> emit = new HashMap<>();
        HashMap<String, HashMap<String, Double>> trans = new HashMap<>();
        readfile(TRAIN_DATA_INPUT_ROOT_PATH
                + CORPUS_DIR_NAME);
        logger.info(corpus.size());
        HashMap<String, Double> tmp;
        for (String sentence : corpus) {
            logger.debug(PinyinHelper.convertToPinyinString(sentence, ",", PinyinFormat.WITHOUT_TONE));
            String[] pyList = PinyinHelper.convertToPinyinString(sentence, ",", PinyinFormat.WITHOUT_TONE).split(",");
            String[] hanziList = sentence.split("");
            if (PI.containsKey(pyList[0])) {
                PI.put(pyList[0], PI.get(pyList[0]) + 1);
            } else {
                PI.put(pyList[0], 1.0);
            }

            for (int i = 0; i < sentence.length() - 1; i++) {
                if (!hanziList[i].equals(" ") && !hanziList[i + 1].equals(" ")) {
                    if (trans.get(hanziList[i]) == null) {
                        tmp = new HashMap<>();
                        tmp.put(hanziList[i + 1], 1.);
                        trans.put(hanziList[i], tmp);
                    } else if (trans.get(hanziList[i]).get(hanziList[i + 1]) == null) {
                        trans.get(hanziList[i]).put(hanziList[i + 1], 1.0);
                    } else {
                        trans.get(hanziList[i]).put(hanziList[i + 1], trans.get(hanziList[i]).get(hanziList[i + 1]) + 1.0);
                    }
                }
            }
            for (int i = 0; i < sentence.length(); i++) {
                if (!pyList[i].equals(" ") && !hanziList[i].equals(" ")) {
                    inputValueToMap(emit, pyList[i], hanziList[i]);

                    if (py2ch.get(pyList[i]) == null) {
                        tmp = new HashMap<String, Double>();
                        tmp.put(hanziList[i], 1.);
                        py2ch.put(pyList[i], tmp);
                    } else if (py2ch.get(pyList[i]).get(hanziList[i]) == null) {
                        py2ch.get(pyList[i]).put(hanziList[i], 1.0);
                    } else {
                        py2ch.get(pyList[i]).put(hanziList[i], py2ch.get(pyList[i]).get(hanziList[i]) + 1.0);
                    }
                }
            }
        }
        System.out.println("");

        List<SplitResultItem> result = getSplitResultItems("beijingdaxue");
        for (SplitResultItem temp : result) {
            System.out.println("result - item:" + temp.getPyItem() + ", type:" + temp.getPyType());
        }
        List<CandidateItem> topKResult = topkPath(result, 15, py2ch, PI, emit, trans);
        for (CandidateItem candidateItem : topKResult) {
            logger.info("candidateItem + " + candidateItem.getCandidateWord());
        }
    }

    private static void inputValueToMap(HashMap<String, HashMap<String, Double>> trans, String key, String key1) {
        boolean b = trans.get(key1) == null;
        HashMap<String, Double> tmp;
        if (b) {
            tmp = new HashMap<String, Double>();
            tmp.put(key, 1.);
            trans.put(key1, tmp);
        } else if (trans.get(key1).get(key) == null) {
            trans.get(key1).put(key, 1.0);
        } else {
            trans.get(key1).put(key, trans.get(key1).get(key) + 1.0);
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
