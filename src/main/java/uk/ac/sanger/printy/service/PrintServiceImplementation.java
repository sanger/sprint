package uk.ac.sanger.printy.service;

import org.springframework.stereotype.Component;
import uk.ac.sanger.printy.model.PrintRequest;
import uk.ac.sanger.printy.model.Printer;

import java.io.IOException;
import java.util.UUID;

@Component
public class PrintServiceImplementation implements PrintService {
    @Override
    public String print(PrintRequest request, Printer printer) throws IOException  {
        String jobId = UUID.randomUUID().toString();
        PrinterLanguageAdapter languageAdapter = PrinterLanguageAdapterFactory.getLanguageAdapter(printer);
        String printCode = languageAdapter.transcribe(request, jobId);

        PrintProtocolAdapter protocolAdapter = PrintProtocolAdapterFactory.getPrintProtocolAdapter(printer);
        protocolAdapter.print(printCode);

        return printer.getHostname()+":"+jobId;
    }

    @Override
    public boolean isJobComplete(Printer printer, String jobId) {
        StatusProtocolAdapter statusProtocolAdapter = StatusProtocolAdapterFactory.getStatusProtocolAdapter(printer);
        return statusProtocolAdapter.isJobComplete(jobId);
    }
}
