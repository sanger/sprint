package uk.ac.sanger.sprint.config;

import uk.ac.sanger.sprint.model.*;
import uk.ac.sanger.sprint.utils.UCMap;

import java.util.List;

/**
 * The config for the application.
 * The printers, printer types and label types, with references to each other.
 */
public class Config {
    private final UCMap<Printer> printers;
    private final List<LabelType> labelTypes;
    private final UCMap<PrinterType> printerTypes;

    public Config(UCMap<Printer> printers, List<LabelType> labelTypes, UCMap<PrinterType> printerTypes) {
        this.printers = printers;
        this.labelTypes = labelTypes;
        this.printerTypes = printerTypes;
    }

    /**
     * The map from printer hostname to printer.
     * @return a map from hostname to printer
     */
    public UCMap<Printer> getPrinters() {
        return printers;
    }

    /**
     * The list of supported label types.
     * @return a list of label types
     */
    public List<LabelType> getLabelTypes() {
        return labelTypes;
    }

    /**
     * The map from printer type name to printer type.
     * @return a map from name to printer type
     */
    public UCMap<PrinterType> getPrinterTypes() {
        return printerTypes;
    }
}
