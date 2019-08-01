package uk.ac.sanger.printy.service;

import org.springframework.stereotype.Component;
import uk.ac.sanger.printy.model.Printer;

@Component
public class PrintProtocolAdapterFactoryImplementation implements PrintProtocolAdapterFactory {
    @Override
    public PrintProtocolAdapter getPrintProtocolAdapter(Printer printer) {
        switch (printer.getPrinterType().getProtocol()) {
            case LDP: return new LpdPrintProtocolAdapter(printer);
            case FTP: return new FtpPrintProtocolAdapter(printer);
        }
        throw new UnsupportedOperationException();
    }
}
