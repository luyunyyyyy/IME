package IMEngine.Algorithm;

import javafx.util.Pair;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

public class PairListComparator implements Comparator<Pair<Double, List<String>>>, Serializable {

//    @Override
//    public int compare(Pair<String, Double> o1, Pair<String, Double> o2) {
//        return (int) (o2.getValue() - o1.getValue());
//    }

    @Override
    public int compare(Pair<Double, List<String>> o1, Pair<Double, List<String>> o2) {
        return (int) (o1.getKey() - o2.getKey());
    }
}
