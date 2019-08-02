package uk.ac.sanger.printy.service.language;

import uk.ac.sanger.printy.model.Printer;

public interface PrinterLanguageAdapterFactory {
    PrinterLanguageAdapter getLanguageAdapter(Printer printer);
}
