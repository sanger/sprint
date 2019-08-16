package uk.ac.sanger.sprint.model;

/**
 * The result of a print request.
 * This may or may not include a job id, which
 * can be used to query the status of a print job.
 */
public class PrintResult {
    private String jobId;

    /**
     * Constructs a print result with no job id
     */
    public PrintResult() {}

    /**
     * Constructs a print result with the given job id
     * @param jobId the job id
     */
    public PrintResult(String jobId) {
        this.jobId = jobId;
    }

    /**
     * Gets the job id for the print job. Not all print results will include a job id.
     * @return the job id, or null
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * Sets the job id for the print job
     * @param jobId the new job id, or null
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    @Override
    public String toString() {
        return String.format("PrintResult{jobId=%s}", jobId);
    }
}
