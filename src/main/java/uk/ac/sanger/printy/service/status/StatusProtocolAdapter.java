package uk.ac.sanger.printy.service.status;

import uk.ac.sanger.printy.model.PrintStatus;

import java.io.IOException;

/**
 * An adapter for getting the print status from a particular printer.
 */
public interface StatusProtocolAdapter {
    /**
     * Gets the print status for the specified job from this adapter's printer.
     * This may return a valid-looking print status even if the job id is not recognised.
     * @param jobId the ID of the job
     * @return a print status describing the status of the print job
     * @exception IOException communication problem
     */
    PrintStatus getPrintStatus(String jobId) throws IOException;
}
