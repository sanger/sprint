package uk.ac.sanger.sprint.service.protocol;

import org.junit.jupiter.api.*;
import org.springframework.boot.ApplicationArguments;
import uk.ac.sanger.sprint.model.*;
import uk.ac.sanger.sprint.service.language.PrinterLanguageAdapterFactory;
import uk.ac.sanger.sprint.utils.UniqueInstantSupplier;

import java.time.Clock;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for the implementation of {@link PrinterLanguageAdapterFactory}
 * @author dr6
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PrintProtocolAdapterFactoryTest {
    private PrintProtocolAdapterFactory protocolAdapterFactory;

    @BeforeEach
    void setup() {
        ApplicationArguments mockApplicationArguments = mock(ApplicationArguments.class);
        when(mockApplicationArguments.getOptionValues("ipAddress")).thenReturn(Collections.singletonList("1.2.3.4"));
        UniqueInstantSupplier instantSupplier = new UniqueInstantSupplier(Clock.systemUTC());
        protocolAdapterFactory = new PrintProtocolAdapterFactoryImplementation(null, mockApplicationArguments, instantSupplier);
    }

    private Printer printer(Protocol protocol) {
        PrinterType pt = new PrinterType("mypt", PrinterLanguage.JSCRIPT, protocol, null, null, null);
        return new Printer("myprinter", pt, null, null);
    }

    @Test
    public void testFtp() {
        PrintProtocolAdapter adapter = protocolAdapterFactory.getPrintProtocolAdapter(printer(Protocol.FTP));
        assertNotNull(adapter);
        assertTrue(adapter instanceof FtpPrintProtocolAdapter);
    }

    @Test
    public void testVolume() {
        PrintProtocolAdapter adapter = protocolAdapterFactory.getPrintProtocolAdapter(printer(Protocol.VOLUME));
        assertNotNull(adapter);
        assertTrue(adapter instanceof VolumeProtocolAdapter);
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
