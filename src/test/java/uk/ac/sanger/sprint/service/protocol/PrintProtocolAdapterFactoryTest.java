package uk.ac.sanger.sprint.service.protocol;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import uk.ac.sanger.sprint.model.*;
import uk.ac.sanger.sprint.service.language.PrinterLanguageAdapterFactory;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Tests for the implementation of {@link PrinterLanguageAdapterFactory}
 * @author dr6
 */
@Test
public class PrintProtocolAdapterFactoryTest {
    private PrintProtocolAdapterFactory protocolAdapterFactory;

    @BeforeClass
    private void setup() {
        protocolAdapterFactory = new PrintProtocolAdapterFactoryImplementation(null);
    }

    @Test
    public void testFtp() {
        PrinterType pt = new PrinterType("mypt", PrinterLanguage.JSCRIPT, Protocol.FTP, null, null);
        Printer printer = new Printer("myprinter", pt, null);
        PrintProtocolAdapter adapter = protocolAdapterFactory.getPrintProtocolAdapter(printer);
        assertNotNull(adapter);
        assertTrue(adapter instanceof FtpPrintProtocolAdapter);
    }

    @Test
    public void testWithNullForPrintProtocol() {
        PrinterType pt = new PrinterType("mypt", PrinterLanguage.JSCRIPT, null, null, null);
        Printer printer = new Printer("myprinter", pt, null);
        UnsupportedOperationException uoe = null;
        try {
            protocolAdapterFactory.getPrintProtocolAdapter(printer);
        } catch (UnsupportedOperationException e) {
            uoe = e;
        }
        assertNotNull(uoe);
    }
}
