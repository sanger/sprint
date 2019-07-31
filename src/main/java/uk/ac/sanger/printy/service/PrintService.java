package uk.ac.sanger.printy.service;

import uk.ac.sanger.printy.model.PrintRequest;
import uk.ac.sanger.printy.model.Printer;

import java.io.IOException;
import java.util.UUID;

public class PrintService {
    /**
     * Prints
     * @param request the specification of what to print
     * @param printer the printer to print to
     * @return the id of the print job
     */
    public String print(PrintRequest request, Printer printer) throws IOException  {
        String jobId = UUID.randomUUID().toString();
        PrinterLanguageAdapter languageAdapter = PrinterLanguageAdapterFactory.getLanguageAdapter(printer);
        String printCode = languageAdapter.transcribe(request, jobId);

        PrintProtocolAdapter protocolAdapter = PrintProtocolAdapterFactory.getPrintProtocolAdapter(printer);
        protocolAdapter.print(printCode);

        return printer.getHostname()+":"+jobId;
    }

    public Boolean isJobComplete(Printer printer, String jobId) {
        StatusProtocolAdapter statusProtocolAdapter = StatusProtocolAdapterFactory.getStatusProtocolAdapter(printer);
        return statusProtocolAdapter.isJobComplete(jobId);
    }
}
