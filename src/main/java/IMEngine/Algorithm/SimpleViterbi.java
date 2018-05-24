package IMEngine.Algorithm;

import Util.CandidateItem;
import Util.SplitResultItem;
import Util.TopKHeap;
import javafx.util.Pair;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static Config.TrainConfig.MIN_PROB;

/*
用于实现一个全拼到汉字序列的转换
 */
public class SimpleViterbi {
    private static Double PI_state(HashMap<String, Double> PI, String state) {
        if (PI.containsKey(state)) {
            return Math.max(PI.get(state), MIN_PROB);
        } else {
            return MIN_PROB;
        }
    }

    /**
     * @param emit    转换矩阵
     * @param state   汉字
     * @param cur_obs 当前的拼音
     * @return 返回的是频数 或最小的一个MIN_PROB
     */
    private static Double emit_a_b(HashMap<String, HashMap<String, Double>> emit, String state, String cur_obs) {
        if (emit.containsKey(state) && emit.get(state).containsKey(cur_obs)) {
            return Math.max(emit.get(state).get(cur_obs), MIN_PROB);

        } else
            return MIN_PROB;

    }

    //    private static Double
    public static void main(String[] args) {

        // topkPath("beijing", 15, )
    }

    /**
     * @param pyList 拼音数组
     * @param top    结果数量
     * @param py2ch  pinyin -> chinese 的概率
     * @param PI     pinyin 的初始概率
     * @param emit   chinese -> pinyin 的概率
     * @return 返回的是频数 或最小的一个MIN_PROB
     */
    public static List<CandidateItem> topkPath(List<SplitResultItem> pyList, int top,
                                               HashMap<String, HashMap<String, Double>> py2ch,
                                               HashMap<String, Double> PI,
                                               HashMap<String, HashMap<String, Double>> emit,
                                               HashMap<String, HashMap<String, Double>> trans) {
        /*
            v 接替表示当前状态和上一状态的结果
         */
        Integer idx = 0;
//        HashMap<String, TopKHeap<Pair<Double, List<String>>>>[] V = new HashMap<?>[2];
        HashMap<String, TopKHeap<Pair<Double, List<String>>>>[] V = (HashMap<String, TopKHeap<Pair<Double, List<String>>>>[])
                Array.newInstance(HashMap.class, 2);
        String cur_obs = pyList.get(0).getPyItem(); // 当前的拼音 第一个位置
        HashMap<String, Double> cur_cand_states = py2ch.get(cur_obs); // 当前拼音对应的汉字集合
        HashMap<String, Double> prevStates;
        Double score;
        ArrayList<String> path;

        Integer pyListLenght = pyList.size();
        TopKHeap<Pair<Double, List<String>>> result = new TopKHeap<>(top, new PairListComparator());
        List<CandidateItem> finalResult = new ArrayList<>();
        HashMap<String, TopKHeap<Pair<Double, List<String>>>> heapHashMap = new HashMap<>();
        V[0] = heapHashMap;
        for (String state : cur_cand_states.keySet()) {
            score = PI_state(PI, state) + emit_a_b(emit, state, cur_obs);    // +
            path = new ArrayList<>();
            path.add(state);
            //new HashMap<>()new TopKHeap<Pair<Double, List<String>>>(top)
            //此处每次都会覆盖
            Double finalScore = score;
            ArrayList<String> finalPath = path;
            heapHashMap.put(state, new TopKHeap<Pair<Double, List<String>>>(top, new PairListComparator()) {{
                add(new Pair<>(finalScore, finalPath));
            }});
            //heapHashMap.get(state).add(new Pair<>(score, path));

            //V.add(0, new PriorityQueue<>());
        }
        for (int i = 1; i < pyListLenght; i++) {
            cur_obs = pyList.get(i).getPyItem();
            idx = i % 2;
            //V[idx] = null;
            prevStates = cur_cand_states;
            cur_cand_states = py2ch.get(cur_obs);
            V[idx] = new HashMap<>();
            for (String curState : cur_cand_states.keySet()) {
                V[(idx)].put(curState, new TopKHeap<>(top, new PairListComparator()));
                for (String prevState : prevStates.keySet()) {
                    for (Pair<Double, List<String>> cand : V[((idx + 1) % 2)].get(prevState)) {
                        score = emit_a_b(emit, curState, cur_obs) + emit_a_b(trans, prevState, curState);
                        Double newScore = score + cand.getKey();
                        ArrayList<String> tempPath;
                        tempPath = new ArrayList<>(cand.getValue());
                        tempPath.add(curState);
                        V[(idx)].get(curState).add(new Pair<>(newScore, tempPath));
                    }
                }
            }
        }
        for (String lastState : V[(idx)].keySet()) {
            for (Pair<Double, List<String>> item : V[idx].get(lastState)) {
                result.add(new Pair<>(item.getKey(), item.getValue()));
            }
        }
//        for (Pair<Double, List<String>> pair : result) {
//            finalResult.add(new CandidateItem(StringUtils.join(new List[]{pair.getValue()}), pair.getKey()));
//        }
        Pair<Double, List<String>> tempPair;
        // poll的时候 result的size会变化 导致迭代次数只有一半
        int size = result.size();
        for (int i = 0; i < size; i++) {
            tempPair = result.poll();
            finalResult.add(new CandidateItem(StringUtils.join(tempPair.getValue(), ""), tempPair.getKey()));
        }
        Collections.reverse(finalResult);
        return finalResult;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
