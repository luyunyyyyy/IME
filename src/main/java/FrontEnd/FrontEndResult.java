package FrontEnd;

import java.util.ArrayList;
import java.util.List;

public class FrontEndResult implements FrontEndResultBase {
    private List<String> result;

    public FrontEndResult(List<String> result) {
        this.result = result;
    }

    public FrontEndResult() {
        result = new ArrayList<String>();
        result.add("北京");
        result.add("背景");
        result.add("背静");
    }

    public List<String> getAllResult() {
        return result;
    }

    public String getMaxResult() {
        return result.get(0);
    }

    public void setResult(List<String> result) {
        this.result = result;
    }
}
