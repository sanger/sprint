package uk.ac.sanger.printy.service;

import uk.ac.sanger.printy.model.Printer;

public class PrintProtocolAdapterFactory {

    public static PrintProtocolAdapter getPrintProtocolAdapter(Printer printer) {
        switch (printer.getPrinterType().getProtocol()) {
            case LDP: return new LpdPrintProtocolAdapter(printer);
            case FTP: return new FtpPrintProtocolAdapter(printer);
        }
        throw new UnsupportedOperationException();
    }

}
