package uk.ac.sanger.sprint.config;

import uk.ac.sanger.sprint.model.*;

import java.util.List;
import java.util.Map;

/**
 * The config for the application.
 * The printers, printer types and label types, with references to each other.
 */
public class Config {
    private Map<String, Printer> printers;
    private List<LabelType> labelTypes;
    private Map<String, PrinterType> printerTypes;

    public Config(Map<String, Printer> printers, List<LabelType> labelTypes, Map<String, PrinterType> printerTypes) {
        this.printers = printers;
        this.labelTypes = labelTypes;
        this.printerTypes = printerTypes;
    }

    /**
     * The map from printer hostname to printer.
     * @return a map from hostname to printer
     */
    public Map<String, Printer> getPrinters() {
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
    public Map<String, PrinterType> getPrinterTypes() {
        return printerTypes;
    }
}
