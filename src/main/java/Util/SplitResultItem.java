package Util;

public class SplitResultItem {

    private String pyItem;
    private PinYinType pyType;

    public String getPyItem() {
        return pyItem;
    }

    public void setPyItem(String pyItem) {
        this.pyItem = pyItem;
    }

    public PinYinType getPyType() {
        return pyType;
    }

    public void setPyType(PinYinType pyType) {
        this.pyType = pyType;
    }

    public SplitResultItem(String pyItem, PinYinType pyType) {
        this.pyItem = pyItem;
        this.pyType = pyType;
    }

    @Override
    public String toString() {
        return "SplitResultItem{" +
                "pyItem='" + pyItem + '\'' +
                ", pyType=" + pyType +
                '}';
    }



}
