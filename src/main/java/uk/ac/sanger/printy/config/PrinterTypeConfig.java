package uk.ac.sanger.printy.config;

import uk.ac.sanger.printy.model.PrinterType;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author dr6
 */
@XmlRootElement(name="printer_types")
public class PrinterTypeConfig {
    private List<PrinterType> printerTypes = new ArrayList<>();

    @XmlElement(name="printer_type")
    public List<PrinterType> getPrinterTypes() {
        return this.printerTypes;
    }

    public void setPrinterTypes(List<PrinterType> printerTypes) {
        this.printerTypes = printerTypes;
    }
    public static Map<String, PrinterType> loadPrinterTypes(Path path) throws JAXBException {
        PrinterTypeConfig ltc = load(path);
        return ltc.getPrinterTypes().stream()
                .collect(Collectors.toMap(PrinterType::getName, Function.identity()));
    }

    public static PrinterTypeConfig load(Path path) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(PrinterTypeConfig.class);
        Unmarshaller um = jc.createUnmarshaller();
        return (PrinterTypeConfig) um.unmarshal(path.toFile());
    }
}
