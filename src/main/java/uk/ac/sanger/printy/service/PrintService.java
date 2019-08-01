package uk.ac.sanger.printy.service;

import uk.ac.sanger.printy.model.*;

import java.io.IOException;

public interface PrintService {
    /**
     * Prints
     * @param request the specification of what to print
     * @param printer the printer to print to
     * @return the id of the print job
     */
    String print(PrintRequest request, Printer printer) throws IOException;

    PrintStatus getPrintStatus(Printer printer, String jobId) throws IOException;
}
