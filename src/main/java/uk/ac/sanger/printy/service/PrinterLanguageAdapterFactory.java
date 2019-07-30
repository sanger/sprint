package uk.ac.sanger.printy.service;

import uk.ac.sanger.printy.model.Printer;

public class PrinterLanguageAdapterFactory {
    public static PrinterLanguageAdapter getLanguageAdapter(Printer printer) {
        switch (printer.getLanguage()) {
            case tec: // TODO
                return null;
            case jscript:
                return new JScriptAdapter(printer);
        }
        throw new UnsupportedOperationException("Unrecognised printer language");
    }
}
