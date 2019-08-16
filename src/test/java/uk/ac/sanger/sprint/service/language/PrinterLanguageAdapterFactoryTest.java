package uk.ac.sanger.sprint.service.language;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import uk.ac.sanger.sprint.model.*;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

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
        PrinterType pt = new PrinterType("mypt", PrinterLanguage.JSCRIPT, Protocol.FTP, null, null);
        Printer printer = new Printer("myprinter", pt, null);
        PrinterLanguageAdapter adapter = languageAdapterFactory.getLanguageAdapter(printer);
        assertNotNull(adapter);
        assertTrue(adapter instanceof JScriptAdapter);
    }

    @Test
    public void testWithNullForPrinterLanguage() {
        PrinterType pt = new PrinterType("mypt", null, Protocol.FTP, null, null);
        Printer printer = new Printer("myprinter", pt, null);
        UnsupportedOperationException uoe = null;
        try {
            languageAdapterFactory.getLanguageAdapter(printer);
        } catch (UnsupportedOperationException e) {
            uoe = e;
        }
        assertNotNull(uoe);
    }
}
