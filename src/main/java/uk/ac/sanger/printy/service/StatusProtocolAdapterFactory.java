package uk.ac.sanger.printy.service;

import uk.ac.sanger.printy.model.Printer;
import uk.ac.sanger.printy.model.StatusProtocol;

public class StatusProtocolAdapterFactory {
    public static StatusProtocolAdapter getStatusProtocolAdapter(Printer printer) {
        if (printer.getPrinterType().getStatusProtocol()==StatusProtocol.SOAP) {
            return new SoapStatusProtocolAdapter(printer);
        }
        throw new UnsupportedOperationException("getStatusProtocolAdapter");
    }
}
