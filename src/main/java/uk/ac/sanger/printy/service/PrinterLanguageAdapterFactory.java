package uk.ac.sanger.printy.service;

import uk.ac.sanger.printy.model.Printer;

public class PrinterLanguageAdapterFactory {
    public static PrinterLanguageAdapter getLanguageAdapter(Printer printer) {
        switch (printer.getPrinterType().getLanguage()) {
            case TEC: // TODO
                return null;
            case JSCRIPT:
                return new JScriptAdapter(printer);
        }
        throw new UnsupportedOperationException("Unrecognised printer language");
    }
}
