package TrainData;

import Util.PinyinSplit;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import static Config.TrainConfig.*;

public class SimpleSpellDict {
    private static Logger logger = Logger.getLogger(SimpleSpellDict.class);

    public static void trainSimpleSpellDict(String filename) throws IOException {

        BufferedReader bufferedReader;
        HashMap<String, ArrayList<Pair<String, Double>>> simpleSpellDict;
        try {
            bufferedReader = new BufferedReader(new FileReader(TRAIN_DATA_INPUT_ROOT_PATH
                    + DICTWORD_FILE_NAME));
            simpleSpellDict = new HashMap<>();
            String tmpStr;
            String[] pinyin;
            Double value;
            String[] tmpArray;
            ArrayList<Pair<String, Double>> arrayList;
            while ((tmpStr = bufferedReader.readLine()) != null) {
                tmpArray = tmpStr.split(",");
                value = Double.parseDouble(tmpArray[1]);
                pinyin = PinyinSplit.converterToFirstSpell(tmpArray[0]);
                for (String py : pinyin) {
                    if (simpleSpellDict.containsKey(py)) {
                        simpleSpellDict.get(py).add(new Pair<>(tmpArray[0], value));
                    } else {
                        arrayList = new ArrayList<>();
                        arrayList.add(new Pair<>(tmpArray[0], value));
                        simpleSpellDict.put(py, arrayList);
                    }
                }
            }
            bufferedReader.close();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TRAIN_DATA_OUTPUT_ROOT_PATH + filename));
            oos.writeObject(simpleSpellDict);                 //将Person对象p写入到oos中
            oos.close();
            logger.debug("simpleSpellDict size : " + simpleSpellDict.size());
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException file name : " + TRAIN_DATA_INPUT_ROOT_PATH
                    + DICTWORD_FILE_NAME);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        //TODO 这个存入的代码有问题, 最后的字典里可能每个拼音只会有一个item 这个保存的对象读取出来会很慢
        /*
            用于简拼模式寻找对应的候选词
            训练字典, 保存到文件中
         */
        trainSimpleSpellDict("simpleSpellDict.dat");
//        ArrayList<SplitResultItem> testInput = new ArrayList<>();
//        testInput.add(new SplitResultItem("t", PinYinType.SIMPLESPELL));
//        testInput.add(new SplitResultItem("j", PinYinType.SIMPLESPELL));
        //new SimpleIMEngineInstance().getCandidateWords(testInput);

//        PriorityQueue priorityQueue = new PriorityQueue(new PairComparator());
//        priorityQueue.add(new Pair<String, Double>("北京",1.0));
//        HashMap<String, PriorityQueue<Pair<String, Double>>> simpleSpellDict = new HashMap<>();
        //simpleSpellDict.put("bj", priorityQueue);
        //new SimpleIMEngineInstance(simpleSpellDict).getCandidateWords(testInput);
    }
    /*
    bugs 1. 集合不能改变, 这样后面再重复调用就会产生问题
         2. 对象读入过慢, 不知道什么问题
         3.
     */
}
