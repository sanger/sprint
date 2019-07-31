package uk.ac.sanger.printy.config;

import uk.ac.sanger.printy.model.*;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author dr6
 */
@XmlRootElement(name="printers")
public class PrinterConfig {
    private static class Entry {
        private String hostname;
        private String labelType;
        private String printerType;

        public String getHostname() {
            return this.hostname;
        }

        public void setHostname(String hostname) {
            this.hostname = hostname;
        }

        public String getLabelType() {
            return this.labelType;
        }

        public void setLabelType(String labelType) {
            this.labelType = labelType;
        }

        public String getPrinterType() {
            return this.printerType;
        }

        public void setPrinterType(String printerType) {
            this.printerType = printerType;
        }
    }

    private List<Entry> entries = new ArrayList<>();

    @XmlElement(name="printer")
    public List<Entry> getEntries() {
        return this.entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }


    public static PrinterConfig load(Path path) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(PrinterConfig.class);
        Unmarshaller um = jc.createUnmarshaller();
        return (PrinterConfig) um.unmarshal(path.toFile());
    }

    public static Map<String, Printer> loadPrinters(Path directory) {
        try {
            final Map<String, LabelType> labelTypes = LabelTypeConfig.loadLabelTypes(directory.resolve("label_types.xml"));
            final Map<String, PrinterType> printerTypes = PrinterTypeConfig.loadPrinterTypes(directory.resolve("printer_types.xml"));
            PrinterConfig pc = load(directory.resolve("printers.xml"));
            return pc.getEntries().stream()
                    .map(entry -> new Printer(entry.getHostname(),
                            printerTypes.get(entry.getPrinterType()),
                            labelTypes.get(entry.getLabelType())))
                    .collect(Collectors.toMap(Printer::getHostname, Function.identity()));
        } catch (JAXBException e) {
            throw new RuntimeException("Couldn't load printer config", e);
        }
    }

    public static void main(String[] args) throws JAXBException {
        Path directory = Paths.get(".");
        System.out.println(loadPrinters(directory));
        // TODO - figure out where config files should be placed
    }
}
