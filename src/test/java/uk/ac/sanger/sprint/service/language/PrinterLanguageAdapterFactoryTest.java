package uk.ac.sanger.sprint.service.language;

import org.junit.jupiter.api.*;
import uk.ac.sanger.sprint.model.*;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the implementation of {@link PrinterLanguageAdapterFactory}
 * @author dr6
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PrinterLanguageAdapterFactoryTest {
    private PrinterLanguageAdapterFactory languageAdapterFactory;

    @BeforeAll
    void setup() {
        languageAdapterFactory = new PrinterLanguageAdapterFactoryImplementation();
    }

    @Test
    public void testJScript() {
        PrinterType pt = new PrinterType("mypt", PrinterLanguage.JSCRIPT, Protocol.FTP, null, null, null);
        Printer printer = new Printer("myprinter", pt, null, null);
        PrinterLanguageAdapter adapter = languageAdapterFactory.getLanguageAdapter(printer);
        assertNotNull(adapter);
        assertTrue(adapter instanceof JScriptAdapter);
    }

    @Test
    public void testCsv() {
        PrinterType pt = new PrinterType("mypy", PrinterLanguage.CSV, Protocol.VOLUME, null, null, null);
        Printer printer = new Printer("myprinter", pt, null, Paths.get("path","printer"));
        PrinterLanguageAdapter adapter = languageAdapterFactory.getLanguageAdapter(printer);
        assertNotNull(adapter);
        assertTrue(adapter instanceof CsvAdapter);
    }

    @Test
    public void testWithNullForPrinterLanguage() {
        PrinterType pt = new PrinterType("mypt", null, Protocol.FTP, null, null, null);
        Printer printer = new Printer("myprinter", pt, null, null);
        assertThrows(UnsupportedOperationException.class, () -> languageAdapterFactory.getLanguageAdapter(printer));
    }
}
