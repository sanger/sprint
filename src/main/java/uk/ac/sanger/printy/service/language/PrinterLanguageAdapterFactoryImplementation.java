package uk.ac.sanger.printy.service.language;

import org.springframework.stereotype.Component;
import uk.ac.sanger.printy.model.Printer;

@Component
public class PrinterLanguageAdapterFactoryImplementation implements PrinterLanguageAdapterFactory {
    @Override
    public PrinterLanguageAdapter getLanguageAdapter(Printer printer) {
        switch (printer.getPrinterType().getLanguage()) {
            case TEC: // TODO
                return null;
            case JSCRIPT:
                return new JScriptAdapter(printer);
        }
        throw new UnsupportedOperationException("Unrecognised printer language");
    }
}
