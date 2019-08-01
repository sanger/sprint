package uk.ac.sanger.printy.config;

import org.springframework.stereotype.Component;
import uk.ac.sanger.printy.model.*;

import javax.xml.bind.JAXBException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Injectable implementation of {@code ConfigLoader}
 * @author dr6
 */
@Component
class ConfigLoaderImplementation implements ConfigLoader {
    @Override
    public Map<String, Printer> getPrinters(Path path) {
        PrinterConfig config;
        try {
            config = PrinterConfig.load(path);
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to load printer config", e);
        }
        final Map<String, LabelType> labelTypes = config.getLabelTypes().stream()
                .collect(Collectors.toMap(LabelType::getName, Function.identity()));
        final Map<String, PrinterType> printerTypes = config.getPrinterTypes().stream()
                .collect(Collectors.toMap(PrinterType::getName, Function.identity()));
        return config.getEntries().stream()
                .map(e -> new Printer(e.getHostname(), printerTypes.get(e.getPrinterType()), labelTypes.get(e.getLabelType())))
                .collect(Collectors.toMap(Printer::getHostname, Function.identity()));
    }
}
