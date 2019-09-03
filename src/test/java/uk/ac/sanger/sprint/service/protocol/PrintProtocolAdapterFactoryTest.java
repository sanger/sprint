package uk.ac.sanger.sprint.service.protocol;

import org.springframework.boot.ApplicationArguments;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import uk.ac.sanger.sprint.model.*;
import uk.ac.sanger.sprint.service.language.PrinterLanguageAdapterFactory;

import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Tests for the implementation of {@link PrinterLanguageAdapterFactory}
 * @author dr6
 */
@Test
public class PrintProtocolAdapterFactoryTest {
    private PrintProtocolAdapterFactory protocolAdapterFactory;

    @BeforeMethod
    private void setup() {
        ApplicationArguments mockApplicationArguments = mock(ApplicationArguments.class);
        when(mockApplicationArguments.getOptionValues("ipAddress")).thenReturn(Collections.singletonList("1.2.3.4"));
        protocolAdapterFactory = new PrintProtocolAdapterFactoryImplementation(null, mockApplicationArguments);
    }

    private Printer printer(Protocol protocol) {
        PrinterType pt = new PrinterType("mypt", PrinterLanguage.JSCRIPT, protocol, null, null);
        return new Printer("myprinter", pt, null);
    }

    @Test
    public void testFtp() {
        PrintProtocolAdapter adapter = protocolAdapterFactory.getPrintProtocolAdapter(printer(Protocol.FTP));
        assertNotNull(adapter);
        assertTrue(adapter instanceof FtpPrintProtocolAdapter);
    }

    @Test
    public void testStub() {
        PrintProtocolAdapter adapter = protocolAdapterFactory.getPrintProtocolAdapter(printer(Protocol.STUB));
        assertNotNull(adapter);
        assertTrue(adapter instanceof StubPrintProtocolAdapter);
    }

    @Test
    public void testWithNoPrintProtocol() {
        UnsupportedOperationException uoe = null;
        Printer printer = printer(null);
        try {
            protocolAdapterFactory.getPrintProtocolAdapter(printer);
        } catch (UnsupportedOperationException e) {
            uoe = e;
        }
        assertNotNull(uoe);
    }
}
