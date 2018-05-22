package Util;

public class SplitResultItem {

    private String pyItem;
    private PinYinItemType pyType;

    public String getPyItem() {
        return pyItem;
    }

    public void setPyItem(String pyItem) {
        this.pyItem = pyItem;
    }

    public SplitResultItem(String pyItem, PinYinItemType pyType) {
        this.pyItem = pyItem;
        this.pyType = pyType;
    }

    public PinYinItemType getPyType() {
        return pyType;
    }

    public void setPyType(PinYinItemType pyType) {
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
