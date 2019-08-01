package uk.ac.sanger.printy.service;

import uk.ac.sanger.printy.model.PrintRequest;
import uk.ac.sanger.printy.model.Printer;

import java.io.IOException;

public interface PrintService {
    /**
     * Prints
     * @param request the specification of what to print
     * @param printer the printer to print to
     * @return the id of the print job
     */
    String print(PrintRequest request, Printer printer) throws IOException;

    boolean isJobComplete(Printer printer, String jobId);
}
