package uk.ac.sanger.printy.service.language;

import org.springframework.stereotype.Component;
import uk.ac.sanger.printy.model.Printer;
import uk.ac.sanger.printy.model.PrinterLanguage;

@Component
public class PrinterLanguageAdapterFactoryImplementation implements PrinterLanguageAdapterFactory {
    @Override
    public PrinterLanguageAdapter getLanguageAdapter(Printer printer) {
        PrinterLanguage language = printer.getPrinterType().getLanguage();
        if (language == PrinterLanguage.JSCRIPT) {
            return new JScriptAdapter(printer);
        }
        throw new UnsupportedOperationException("Unsupported printer language: "+language);
    }
}
