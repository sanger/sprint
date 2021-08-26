package uk.ac.sanger.sprint.config;

import org.springframework.stereotype.Component;
import uk.ac.sanger.sprint.model.*;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
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
                if (Files.isDirectory(path)) {
                    Iterator<Path> pathIter = Files.list(path)
                            .filter(subPath -> Files.isRegularFile(subPath) && hasExtension(subPath, ".xml"))
                            .iterator();
                    while (pathIter.hasNext()) {
                        configs.add(printerConfigLoader.apply(pathIter.next()));
                    }
                } else {
                    configs.add(printerConfigLoader.apply(path));
                }
            }
        } catch (JAXBException|IOException e) {
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
                .map(e -> toPrinter(e, printerTypes, labelTypes))
                .collect(Collectors.toMap(Printer::getHostname, Function.identity()));

        for (Printer printer: printers.values()) {
            printer.getLabelType().getPrinters().add(printer);
            printer.getPrinterType().getPrinters().add(printer);
        }

        return new Config(printers, new ArrayList<>(labelTypes.values()), printerTypes);
    }

    private static Printer toPrinter(PrinterEntry e, Map<String, PrinterType> printerTypes, Map<String, LabelType> labelTypes) {
        PrinterType printerType = printerTypes.get(e.getPrinterType());
        if (printerType==null) {
            throw new RuntimeException("Unknown printer type: "+e.getPrinterType());
        }
        LabelType labelType = labelTypes.get(e.getLabelType());
        if (labelType==null) {
            throw new RuntimeException("Unknown label type: "+e.getLabelType());
        }
        return new Printer(e.getHostname(), printerType, labelType);
    }

    private static boolean hasExtension(Path path, String extension) {
        String filename = path.getFileName().toString();
        int xl = extension.length();
        int fl = filename.length();
        if (fl < xl) {
            return false;
        }
        return filename.regionMatches(true, fl - xl, extension, 0, xl);
    }
}
