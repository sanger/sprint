package uk.ac.sanger.sprint.config;

import uk.ac.sanger.sprint.model.LabelType;
import uk.ac.sanger.sprint.model.PrinterType;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * A class representing the config as read from a config file,
 * which is a collection of label types, printer types,
 * and printer entries.
 * @author dr6
 */
@XmlRootElement(name="config")
class PrinterConfig {
    private List<PrinterEntry> entries = new ArrayList<>();
    private List<LabelType> labelTypes = new ArrayList<>();
    private List<PrinterType> printerTypes = new ArrayList<>();

    @XmlElement(name="printer")
    public List<PrinterEntry> getEntries() {
        return this.entries;
    }
    public void setEntries(List<PrinterEntry> entries) {
        this.entries = entries;
    }

    @XmlElement(name="label_type")
    public List<LabelType> getLabelTypes() {
        return this.labelTypes;
    }
    public void setLabelTypes(List<LabelType> labelTypes) {
        this.labelTypes = labelTypes;
    }

    @XmlElement(name="printer_type")
    public List<PrinterType> getPrinterTypes() {
        return this.printerTypes;
    }
    public void setPrinterTypes(List<PrinterType> printerTypes) {
        this.printerTypes = printerTypes;
    }

    public static PrinterConfig load(Path path) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(PrinterConfig.class);
        Unmarshaller um = jc.createUnmarshaller();
        return (PrinterConfig) um.unmarshal(path.toFile());
    }
}
