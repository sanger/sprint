package uk.ac.sanger.sprint.config;

import org.springframework.stereotype.Component;
import uk.ac.sanger.sprint.model.*;

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
    private ThrowingFunction<Path, PrinterConfig, JAXBException> printerConfigLoader;

    public ConfigLoaderImplementation() {
        this(null);
    }

    public ConfigLoaderImplementation(ThrowingFunction<Path, PrinterConfig, JAXBException> printerConfigLoader) {
        this.printerConfigLoader = (printerConfigLoader==null ? PrinterConfig::load : printerConfigLoader);
    }

    @Override
    public Config loadConfig(Collection<Path> paths) {
        List<PrinterConfig> configs = new ArrayList<>(paths.size());
        try {
            for (Path path : paths) {
                configs.add(printerConfigLoader.apply(path));
            }
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to load printer config", e);
        }

        final Map<String, LabelType> labelTypes = configs.stream()
                .flatMap(cf -> cf.getLabelTypes().stream())
                .collect(Collectors.toMap(LabelType::getName, Function.identity()));
        final Map<String, PrinterType> printerTypes = configs.stream()
                .flatMap(cf -> cf.getPrinterTypes().stream())
                .collect(Collectors.toMap(PrinterType::getName, Function.identity()));
        Map<String, Printer> printers = configs.stream()
                .flatMap(cf -> cf.getEntries().stream())
                .map(e -> new Printer(e.getHostname(), printerTypes.get(e.getPrinterType()), labelTypes.get(e.getLabelType())))
                .collect(Collectors.toMap(Printer::getHostname, Function.identity()));

        for (Printer printer: printers.values()) {
            printer.getLabelType().getPrinters().add(printer);
            printer.getPrinterType().getPrinters().add(printer);
        }

        return new Config(printers, new ArrayList<>(labelTypes.values()), printerTypes);
    }
}
