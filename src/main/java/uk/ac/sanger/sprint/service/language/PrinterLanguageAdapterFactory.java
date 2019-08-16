package uk.ac.sanger.sprint.service.language;

import uk.ac.sanger.sprint.model.Printer;

/**
 * Factory to supply language adapters for particular printers.
 */
public interface PrinterLanguageAdapterFactory {
    /**
     * Returns a language adapter suitable for the given printer.
     * @param printer the printer we want to print to
     * @return a language adapter suitable for the given printer
     * @exception UnsupportedOperationException there is no adapter available for the printer
     */
    PrinterLanguageAdapter getLanguageAdapter(Printer printer);
}
