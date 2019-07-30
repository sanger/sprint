package uk.ac.sanger.printy.service;

import uk.ac.sanger.printy.model.Printer;

public class PrintProtocolAdapterFactory {

    public static PrintProtocolAdapter getPrintProtocolAdapter(Printer printer) {
        switch (printer.getProtocol()) {
            case lpd: return new LpdPrintProtocolAdapter(printer);
            case ftp: return new FtpPrintProtocolAdapter(printer);
        }
        throw new UnsupportedOperationException();
    }

}
