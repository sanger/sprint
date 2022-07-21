package uk.ac.sanger.sprint.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import uk.ac.sanger.sprint.model.*;
import uk.ac.sanger.sprint.service.language.PrinterLanguageAdapter;
import uk.ac.sanger.sprint.service.language.PrinterLanguageAdapterFactory;
import uk.ac.sanger.sprint.service.protocol.PrintProtocolAdapter;
import uk.ac.sanger.sprint.service.protocol.PrintProtocolAdapterFactory;
import uk.ac.sanger.sprint.service.status.StatusProtocolAdapter;
import uk.ac.sanger.sprint.service.status.StatusProtocolAdapterFactory;

import java.io.IOException;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Component
public class PrintServiceImplementation implements PrintService {
    Logger log = LoggerFactory.getLogger(PrintServiceImplementation.class);

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
        log.info("Print to {}: {}", printer.getHostname(), request);
        String jobId = UUID.randomUUID().toString();
        PrinterLanguageAdapter languageAdapter = printerLanguageAdapterFactory.getLanguageAdapter(printer);
        String printCode = languageAdapter.transcribe(request, jobId);

        PrintProtocolAdapter protocolAdapter = printProtocolAdapterFactory.getPrintProtocolAdapter(printer);
        protocolAdapter.print(printCode);
        if (printer.getPrinterType().getStatusProtocol()==null) {
            return null; // Don't return job id if it's not useful
        }

        return printer.getHostname() + ":" +jobId;
    }

    @Override
    public PrintStatus getPrintStatus(Printer printer, String jobId) throws IOException {
        StatusProtocolAdapter statusProtocolAdapter = statusProtocolAdapterFactory.getStatusProtocolAdapter(printer);
        return statusProtocolAdapter.getPrintStatus(jobId);
    }
}
