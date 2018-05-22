package Util;

import javafx.util.Pair;

import java.io.Serializable;
import java.util.Comparator;

public class PairComparator implements Comparator<Pair<String, Double>>, Serializable {

    @Override
    public int compare(Pair<String, Double> o1, Pair<String, Double> o2) {
        return (int) (o2.getValue() - o1.getValue());
    }
}
