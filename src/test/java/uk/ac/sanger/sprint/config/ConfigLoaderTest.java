package uk.ac.sanger.sprint.config;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import uk.ac.sanger.sprint.model.*;

import javax.xml.bind.JAXBException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

/**
 * Tests for the {@link ConfigLoaderImplementation}.
 * @author dr6
 */
@Test
public class ConfigLoaderTest {
    private ConfigLoader configLoader;
    private List<Path> paths;
    private List<PrinterType> printerTypes;
    private List<LabelType> labelTypes;
    private List<Printer> printers;

    @BeforeClass
    private void setup() {
        printerTypes = Arrays.asList(
                new PrinterType("PT0", PrinterLanguage.JSCRIPT, Protocol.FTP, null, new Credentials("alpha", "beta")),
                new PrinterType("PT1", PrinterLanguage.JSCRIPT, Protocol.FTP, StatusProtocol.SOAP, null)
        );
        labelTypes = Arrays.asList(
                new LabelType(10, 20, 30, "lt0"),
                new LabelType(12, 22, 32, "lt1")
        );
        printers = Arrays.asList(
                new Printer("p0", printerTypes.get(0), labelTypes.get(0)),
                new Printer("p1", printerTypes.get(0), labelTypes.get(1)),
                new Printer("p2", printerTypes.get(1), labelTypes.get(0))
        );

        paths = Arrays.asList(Paths.get("printers", "printers.xml"), Paths.get("boo"));
        configLoader = new ConfigLoaderImplementation(this::loadPrinterConfig);
    }

    private static PrinterEntry entry(Printer printer) {
        PrinterEntry entry = new PrinterEntry();
        entry.setHostname(printer.getHostname());
        entry.setLabelType(printer.getLabelType().getName());
        entry.setPrinterType(printer.getPrinterType().getName());
        return entry;
    }

    private static List<PrinterEntry> entries(List<Printer> printers) {
        return printers.stream().map(ConfigLoaderTest::entry).collect(Collectors.toList());
    }

    private PrinterConfig loadPrinterConfig(Path path) throws JAXBException {
        if (path.equals(paths.get(0))) {
            PrinterConfig pc = new PrinterConfig();
            pc.setPrinterTypes(printerTypes.subList(0,1));
            pc.setLabelTypes(labelTypes.subList(0,1));
            pc.setEntries(entries(printers.subList(0,1)));
            return pc;
        }
        if (path.equals(paths.get(1))) {
            PrinterConfig pc = new PrinterConfig();
            pc.setPrinterTypes(printerTypes.subList(1,2));
            pc.setLabelTypes(labelTypes.subList(1,2));
            pc.setEntries(entries(printers.subList(1,3)));
            return pc;
        }
        throw new JAXBException("Bad config: "+path);
    }

    @Test
    public void testLoadConfig() {
        Config config = configLoader.loadConfig(paths);
        Map<String, PrinterType> printerTypeMap = printerTypes.stream()
                .collect(Collectors.toMap(PrinterType::getName, Function.identity()));
        Map<String, Printer> printerMap = printers.stream()
                .collect(Collectors.toMap(Printer::getHostname, Function.identity()));
        assertEquals(config.getPrinterTypes(), printerTypeMap);
        assertEquals(config.getLabelTypes().size(), labelTypes.size());
        assertEquals(new HashSet<>(config.getLabelTypes()), new HashSet<>(labelTypes));
        assertEquals(config.getPrinters(), printerMap);
    }

    @Test
    public void testLoadConfigException() {
        RuntimeException rte = null;
        try {
            configLoader.loadConfig(Arrays.asList(paths.get(0), Paths.get("beep")));
        } catch (RuntimeException e) {
            rte = e;
        }
        assertNotNull(rte);
        assertEquals(rte.getMessage(), "Failed to load printer config");
        assertTrue(rte.getCause() instanceof JAXBException);
    }

    @Test
    public void testLoadConfigDirectory() {
        Config config = configLoader.loadConfig(Collections.singletonList(Paths.get("printers")));
        assertFalse(config.getPrinters().isEmpty());
    }
}
