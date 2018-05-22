package IMEngine;

import Util.CandidateItem;
import Util.SplitResultItem;

import java.util.List;

public interface IMEngineInstance {
    @Deprecated
    List<String> getCandidateWord(String pySeries);

    List<CandidateItem> getCandidateWords(List<SplitResultItem> splitResultItems);
}
