package uk.ac.sanger.sprint.model;

public class PrintResult {
    private String jobId;

    public PrintResult() {}

    public PrintResult(String jobId) {
        this.jobId = jobId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    @Override
    public String toString() {
        return String.format("PrintResult{jobId=%s}", jobId);
    }
}
