package uk.ac.sanger.sprint.service.language;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import uk.ac.sanger.sprint.model.*;

import java.nio.file.Paths;

import static org.testng.Assert.*;

/**
 * Tests for the implementation of {@link PrinterLanguageAdapterFactory}
 * @author dr6
 */
@Test
public class PrinterLanguageAdapterFactoryTest {
    private PrinterLanguageAdapterFactory languageAdapterFactory;

    @BeforeClass
    private void setup() {
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
