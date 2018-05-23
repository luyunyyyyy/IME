package Util;

public class CandidateItem {

    // 这个变量有问题, 关系整错了 应该是一个候选词包含一个词的拼音. 这个变量不能用
    // @Deprecated
    // private SplitResultItem splitResult;
    private String candidateWord;
    private Double score;

    public CandidateItem() {
    }

    public CandidateItem(String candidateWord) {
        this.candidateWord = candidateWord;
    }

    public CandidateItem(String candidateWord, Double score) {
        this.candidateWord = candidateWord;
        this.score = score;
    }

    @Override
    public String toString() {
        return "CandidateItem{" +
                "candidateWord='" + candidateWord + '\'' +
                ", score=" + score +
                '}';
    }

//  public String getPinYinItem() {
//      return this.getSplitResult().getPyItem();
//  }
//  public Enum getPinYinType() {
//      return this.getSplitResult().getPyType();
//  }

    //  public SplitResultItem getSplitResult() {
//      return splitResult;
//  }
//
//  public void setSplitResult(SplitResultItem splitResult) {
//      this.splitResult = splitResult;
//  }
    public String getCandidateWord() {
        return candidateWord;
    }

    public void setCandidateWord(String candidateWord) {
        this.candidateWord = candidateWord;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
