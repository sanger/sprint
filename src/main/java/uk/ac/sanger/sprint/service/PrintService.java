package uk.ac.sanger.sprint.service;

import uk.ac.sanger.sprint.model.*;

import java.io.IOException;

/**
 * A service for handling communicating with printers
 */
public interface PrintService {
    /**
     * Prints
     * @param request the specification of what to print
     * @param printer the printer to print to
     * @return the id of the print job
     * @exception IOException communication problem
     */
    String print(PrintRequest request, Printer printer) throws IOException;

    /**
     * Gets the status of a print job.
     * @param printer the printer to query
     * @param jobId the id of the print job
     * @return a print status describing the status of the print job
     * @exception IOException communication problem
     */
    PrintStatus getPrintStatus(Printer printer, String jobId) throws IOException;
}
