package uk.ac.sanger.printy.service;

import org.springframework.stereotype.Component;
import uk.ac.sanger.printy.model.PrintRequest;
import uk.ac.sanger.printy.model.Printer;

import java.io.IOException;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Component
public class PrintServiceImplementation implements PrintService {
    private final PrinterLanguageAdapterFactory printerLanguageAdapterFactory;
    private final PrintProtocolAdapterFactory printProtocolAdapterFactory;
    private final StatusProtocolAdapterFactory statusProtocolAdapterFactory;

    public PrintServiceImplementation(PrinterLanguageAdapterFactory printerLanguageAdapterFactory,
                                      PrintProtocolAdapterFactory printProtocolAdapterFactory,
                                      StatusProtocolAdapterFactory statusProtocolAdapterFactory) {
        this.printerLanguageAdapterFactory = requireNonNull(printerLanguageAdapterFactory, "printerLanguageAdapterFactory is null");
        this.printProtocolAdapterFactory = requireNonNull(printProtocolAdapterFactory, "printProtocolAdapterFactory is null");
        this.statusProtocolAdapterFactory = requireNonNull(statusProtocolAdapterFactory, "statusProtocolAdapterFactory is null");
    }

    @Override
    public String print(PrintRequest request, Printer printer) throws IOException  {
        String jobId = UUID.randomUUID().toString();
        PrinterLanguageAdapter languageAdapter = printerLanguageAdapterFactory.getLanguageAdapter(printer);
        String printCode = languageAdapter.transcribe(request, jobId);

        PrintProtocolAdapter protocolAdapter = printProtocolAdapterFactory.getPrintProtocolAdapter(printer);
        protocolAdapter.print(printCode);

        return printer.getHostname()+":"+jobId;
    }

    @Override
    public boolean isJobComplete(Printer printer, String jobId) {
        StatusProtocolAdapter statusProtocolAdapter = statusProtocolAdapterFactory.getStatusProtocolAdapter(printer);
        return statusProtocolAdapter.isJobComplete(jobId);
    }
}
