package uk.ac.sanger.sprint.service.language;

import org.springframework.stereotype.Component;
import uk.ac.sanger.sprint.model.Printer;
import uk.ac.sanger.sprint.model.PrinterLanguage;

@Component
public class PrinterLanguageAdapterFactoryImplementation implements PrinterLanguageAdapterFactory {
    @Override
    public PrinterLanguageAdapter getLanguageAdapter(Printer printer) {
        PrinterLanguage language = printer.getPrinterType().getLanguage();
        if (language!=null) {
            switch (language) {
                case JSCRIPT: return new JScriptAdapter(printer);
                case CSV: return new CsvAdapter(",");
            }
        }
        throw new UnsupportedOperationException("Unsupported printer language: "+language);
    }
}
