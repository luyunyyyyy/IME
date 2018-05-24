package TrainData;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import static Config.TrainConfig.*;
import static java.lang.Math.log;

public class TrainPyChData {
    private static ArrayList<String> corpus = new ArrayList<>();
    private static Logger logger = Logger.getLogger(TrainPyChData.class);
    public TrainPyChData() {

    }

    public static void trainData() throws IOException {
        HashMap<String, HashMap<String, Double>> py2ch = new HashMap<>();
        HashMap<String, Double> PI = new HashMap<>();
        HashMap<String, HashMap<String, Double>> emit = new HashMap<>();
        HashMap<String, HashMap<String, Double>> trans = new HashMap<>();
        readfile(TRAIN_DATA_INPUT_ROOT_PATH
                + CORPUS_DIR_NAME);
        logger.info(corpus.size());
        HashMap<String, Double> tmp;
        Pattern pattern = Pattern.compile("[^\\u4e00-\\u9fa5]");
        for (String sentence : corpus) {
            String[] pyList;
            sentence = pattern.matcher(sentence).replaceAll("");
            try {
                //logger.debug(PinyinHelper.convertToPinyinString(sentence, ",", PinyinFormat.WITHOUT_TONE));
                pyList = PinyinHelper.convertToPinyinString(sentence, ",", PinyinFormat.WITHOUT_TONE).split(",");
                String[] hanziList = sentence.split("");
                logger.debug("pylist + " + StringUtils.join(pyList, ","));
                /*
                初始化PI
                PI的初始化是以每行的第一个字符开始

                 */
                if (PI.containsKey(hanziList[0])) {
                    PI.put(hanziList[0], PI.get(hanziList[0]) + 1);
                } else {
                    PI.put(hanziList[0], 1.0);
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
                            tmp = new HashMap<>();
                            tmp.put(hanziList[i], 1.);
                            py2ch.put(pyList[i], tmp);
                        } else if (py2ch.get(pyList[i]).get(hanziList[i]) == null) {
                            py2ch.get(pyList[i]).put(hanziList[i], 1.0);
                        } else {
                            py2ch.get(pyList[i]).put(hanziList[i], py2ch.get(pyList[i]).get(hanziList[i]) + 1.0);
                        }
                    }
                }
            } catch (PinyinException e) {
                logger.error(e.toString());
                e.printStackTrace();
            }


        }
        Double piCount = 0.0;
        for (String key : PI.keySet()) {
            piCount += PI.get(key);
        }
        for (String key : PI.keySet()) {
            PI.put(key, log(PI.get(key) / piCount));
        }
        numberToRate(emit);
        numberToRate(py2ch);
        numberToRate(trans);
        Object[] data = {PI, emit, py2ch, trans};
        String[] filename = {PI_FILE_NAME, EMIT_FILE_NAME, PY2CH_FILE_NAME, TRANS_FILE_NAME};
        for (int i = 0; i < filename.length; i++) {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TRAIN_DATA_OUTPUT_ROOT_PATH + filename[i]));
            oos.writeObject(data[i]);                 //将Person对象p写入到oos中
            oos.close();
        }
//        ArrayList data = new ArrayList();
//        data.add(PI);
//        data.add()
//        data.add(emit);
//        data.add()
    }

    public static void main(String[] args) {
        //trainData();

//        System.out.println("");
//
//        List<SplitResultItem> result = getSplitResultItems("zhongguancunruanjianyuanzuanshidasha");
//        for (SplitResultItem temp : result) {
//            System.out.println("result - item:" + temp.getPyItem() + ", type:" + temp.getPyType());
//        }
//        List<CandidateItem> topKResult = topkPath(result, SimpleConfig.SIMPLE_SPELL_CANDIDATE_COUNT, py2ch, PI, emit, trans);
//        Set<String> resultSet = new HashSet<>();
//        for (CandidateItem candidateItem : topKResult) {
//            resultSet.add(candidateItem.getCandidateWord());
//            logger.info("candidateItem + " + candidateItem.getCandidateWord() + " score : " + candidateItem.getScore());
//        }
//        logger.info("candidateItems size : " + topKResult.size());
//        logger.info("resultSet size : " + resultSet.size());
    }

    public static void loadData(HashMap<String, HashMap<String, Double>> py2ch,
                                HashMap<String, Double> PI,
                                HashMap<String, HashMap<String, Double>> emit,
                                HashMap<String, HashMap<String, Double>> trans) {

    }

    private static void numberToRate(HashMap<String, HashMap<String, Double>> emit) {
        for (String start : emit.keySet()) {
            Double count = 0.0;
            for (String end : emit.get(start).keySet()) {
                count += emit.get(start).get(end);
            }
            for (String end : emit.get(start).keySet()) {
                emit.get(start).put(end, log(emit.get(start).get(end) / count));
            }
        }
    }

    private static void inputValueToMap(HashMap<String, HashMap<String, Double>> trans, String key, String key1) {
        boolean b = trans.get(key1) == null;
        HashMap<String, Double> tmp;
        if (b) {
            tmp = new HashMap<>();
            tmp.put(key, 1.);
            trans.put(key1, tmp);
        } else if (trans.get(key1).get(key) == null) {
            trans.get(key1).put(key, 1.0);
        } else {
            trans.get(key1).put(key, trans.get(key1).get(key) + 1.0);
        }
    }


    /**
     * 传入一个路径, 读取所有的结果到一个数组中
     * 如果路径对应一个文件夹则递归的读取文件夹内所有数据
     * 需要一个驱动程序, 把这个方法的静态变量给保存起来.
     *
     * @param filepath 传入文件路径
     * @throws IOException
     */
    public static void readfile(String filepath) throws IOException {
        try {

            File file = new File(filepath);
            if (!file.isDirectory()) {
//                System.out.println("文件");
//                System.out.println("path=" + file.getPath());
//                System.out.println("absolutepath=" + file.getAbsolutePath());
//                System.out.println("name=" + file.getName());
                logger.debug("filepath = " + file.getPath());
                logger.debug("name = " + file.getName());
            } else if (file.isDirectory()) {
                logger.debug("读取文件夹内容");
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) {
                        logger.debug("filepath = " + readfile.getPath());
                        logger.debug("name = " + readfile.getName());
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(readfile));
                        String tmpStr;
                        int line_id = 0;
                        while ((tmpStr = bufferedReader.readLine()) != null) {
                            line_id += 1;
                            if (line_id % 100000 == 0)
                                logger.info("file lines : " + line_id);
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
    }

}
