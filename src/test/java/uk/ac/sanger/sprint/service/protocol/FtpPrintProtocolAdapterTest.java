package uk.ac.sanger.sprint.service.protocol;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import uk.ac.sanger.sprint.model.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
/**
 * Tests for the {@link FtpPrintProtocolAdapter}
 * @author dr6
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FtpPrintProtocolAdapterTest {
    private static final String IPADDRESS = "1.2.3.4";

    @Mock
    private FTPStore mockFtpStore;
    @Mock
    private FTPStoreFactory mockFtpStoreFactory;

    private Credentials credentials;
    private Printer printer;

    private FtpPrintProtocolAdapter adapter;
    private AutoCloseable mocking;

    @BeforeAll
    void setup() throws IOException {
        credentials = new Credentials("jeff", "swordfish");
        PrinterType printerType = new PrinterType("ftpprinter", PrinterLanguage.JSCRIPT, Protocol.FTP,
                null, credentials, null);
        LabelType labelType = new LabelType(30, 12, 16, "tiny");
        printer = new Printer("myprinter", printerType, labelType);
    }

    @BeforeEach
    void setupMocks() {
        mocking = openMocks(this);
        when(mockFtpStoreFactory.getFTPStore(any(), any(), any())).thenReturn(mockFtpStore);
        adapter = new FtpPrintProtocolAdapter(printer, mockFtpStoreFactory, IPADDRESS);
    }

    @AfterEach
    void closeMocks() throws Exception {
        mocking.close();
    }

    @Test
    public void testPrintSuccessful() throws IOException {
        when(mockFtpStore.put(any(), any())).thenReturn(true);

        String printCode = "P\nABC 123\nX\n";
        adapter.print(printCode);
        verify(mockFtpStoreFactory).getFTPStore(printer.getAddress(), credentials, IPADDRESS);
        verify(mockFtpStore).put(eq(printCode), notNull());
    }

    @Test
    public void testPrintUnsuccessful() throws IOException {
        when(mockFtpStore.put(any(), any())).thenReturn(false);

        String printCode = "P\nABC 123\nX\n";
        IOException exception = null;
        try {
            adapter.print(printCode);
        } catch (IOException e) {
            exception = e;
        }
        assertNotNull(exception);
        verify(mockFtpStoreFactory).getFTPStore(printer.getAddress(), credentials, IPADDRESS);
        verify(mockFtpStore).put(eq(printCode), notNull());
    }
}
