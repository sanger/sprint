package uk.ac.sanger.sprint.config;

import uk.ac.sanger.sprint.model.LabelType;
import uk.ac.sanger.sprint.model.Printer;

import java.util.List;
import java.util.Map;

public class Config {
    private Map<String, Printer> printers;
    private List<LabelType> labelTypes;

    public Config(Map<String, Printer> printers, List<LabelType> labelTypes) {
        this.printers = printers;
        this.labelTypes = labelTypes;
    }

    public Map<String, Printer> getPrinters() {
        return printers;
    }

    public List<LabelType> getLabelTypes() {
        return labelTypes;
    }
}
