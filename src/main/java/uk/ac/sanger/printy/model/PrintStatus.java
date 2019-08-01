package uk.ac.sanger.printy.model;

public class PrintStatus {
    private int numFinished;
    private int numAborted;

    public PrintStatus() {}

    public PrintStatus(int numFinished, int numAborted) {
        this.numFinished = numFinished;
        this.numAborted = numAborted;
    }

    public int getNumFinished() {
        return this.numFinished;
    }

    public void setNumFinished(int numFinished) {
        this.numFinished = numFinished;
    }

    public int getNumAborted() {
        return this.numAborted;
    }

    public void setNumAborted(int numAborted) {
        this.numAborted = numAborted;
    }

    @Override
    public String toString() {
        return String.format("PrintStatus{numFinished=%s, numAborted=%s}", numFinished, numAborted);
    }
}
