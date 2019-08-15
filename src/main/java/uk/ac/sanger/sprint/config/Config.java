package uk.ac.sanger.sprint.config;

import uk.ac.sanger.sprint.model.LabelType;
import uk.ac.sanger.sprint.model.Printer;
import uk.ac.sanger.sprint.model.PrinterType;

import java.util.List;
import java.util.Map;

public class Config {
    private Map<String, Printer> printers;
    private List<LabelType> labelTypes;
    private Map<String, PrinterType> printerTypes;

    public Config(Map<String, Printer> printers, List<LabelType> labelTypes, Map<String, PrinterType> printerTypes) {
        this.printers = printers;
        this.labelTypes = labelTypes;
        this.printerTypes = printerTypes;
    }

    public Map<String, Printer> getPrinters() {
        return printers;
    }

    public List<LabelType> getLabelTypes() {
        return labelTypes;
    }

    public Map<String, PrinterType> getPrinterTypes() {
        return printerTypes;
    }
}
