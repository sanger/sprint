package uk.ac.sanger.sprint.model;

/**
 * The result of a print job status request.
 */
public class PrintStatus {
    private int numFinished;
    private int numAborted;

    /**
     * Constructs a print status instance with default values
     */
    public PrintStatus() {}

    /**
     * Constructs a print status instance with the given values for its fields
     * @param numFinished the number of labels that have been printed
     * @param numAborted the number of labels that have been aborted
     */
    public PrintStatus(int numFinished, int numAborted) {
        this.numFinished = numFinished;
        this.numAborted = numAborted;
    }

    /**
     * Gets the number of labels that have been printed
     * @return the number of labels that have been printed
     */
    public int getNumFinished() {
        return this.numFinished;
    }

    /**
     * Sets the number of labels that have been printed
     * @param numFinished the number of labels that have been printed
     */
    public void setNumFinished(int numFinished) {
        this.numFinished = numFinished;
    }

    /**
     * Gets the number of labels that have been aborted
     * @return the number of labels that have been aborted
     */
    public int getNumAborted() {
        return this.numAborted;
    }

    /**
     * Sets the number of labels that have been aborted
     * @param numAborted the number of labels that have been aborted
     */
    public void setNumAborted(int numAborted) {
        this.numAborted = numAborted;
    }

    @Override
    public String toString() {
        return String.format("PrintStatus{numFinished=%s, numAborted=%s}", numFinished, numAborted);
    }
}
