package Util;

public class CandidateItem {
    private SplitResultItem splitResult;
    private String candidateWord;
    private Double score;

    public CandidateItem() {
    }

    public CandidateItem(SplitResultItem splitResult, String candidateWord, Double score) {
        this.splitResult = splitResult;
        this.candidateWord = candidateWord;
        this.score = score;
    }

    public SplitResultItem getSplitResult() {
        return splitResult;
    }

    public void setSplitResult(SplitResultItem splitResult) {
        this.splitResult = splitResult;
    }

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

    public String getPinYinItem() {
        return this.getSplitResult().getPyItem();
    }
    public Enum getPinYinType() {
        return this.getSplitResult().getPyType();
    }
    @Override
    public String toString() {
        return "CandidateItem{" +
                "splitResult=" + splitResult +
                ", candidateWord='" + candidateWord + '\'' +
                ", score=" + score +
                '}';
    }
}
