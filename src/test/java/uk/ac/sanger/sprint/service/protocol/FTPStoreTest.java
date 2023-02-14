package uk.ac.sanger.sprint.service.protocol;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.jupiter.api.*;
import org.mockito.InOrder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link FTPStore}
 * @author dr6
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FTPStoreTest {
    private final String HOST = "myhost", USER = "myuser", PW = "mypw", IPADDRESS = "1.2.3.4";
    private final int TIMEOUT = 1500;
    private final String FILENAME = "myfile", CONTENT = "P\nT 123;ABC\nA 1\n";
    private FTPStore ftpStore;
    private FTPClient mockFtpClient;
    private int contentLength;
    private byte[] contentBytes = new byte[32];

    @BeforeEach
    void setupMocks() {
        ftpStore = new FTPStore(HOST, USER, PW, IPADDRESS);
        mockFtpClient = mock(FTPClient.class);
        ftpStore.setFtpClientSupplier(() -> mockFtpClient);
        ftpStore.setTimeout(TIMEOUT);
    }

    @Test
    public void testPutSucceeds() throws IOException {
        when(mockFtpClient.isConnected()).thenReturn(true);
        when(mockFtpClient.login(USER, PW)).thenReturn(true);
        when(mockFtpClient.storeFile(any(), any())).thenAnswer(invocation -> {
            InputStream is = invocation.getArgument(1);
            contentLength = is.read(contentBytes);
            return true;
        });

        assertTrue(ftpStore.put(CONTENT, FILENAME));

        InOrder inOrder = inOrder(mockFtpClient);

        inOrder.verify(mockFtpClient).setConnectTimeout(TIMEOUT);
        inOrder.verify(mockFtpClient).setDataTimeout(TIMEOUT);
        inOrder.verify(mockFtpClient).setDefaultTimeout(TIMEOUT);
        inOrder.verify(mockFtpClient).connect(HOST);
        inOrder.verify(mockFtpClient).enterLocalActiveMode();
        inOrder.verify(mockFtpClient).setReportActiveExternalIPAddress(IPADDRESS);
        inOrder.verify(mockFtpClient).login(USER, PW);
        inOrder.verify(mockFtpClient).storeFile(eq(FILENAME), notNull());
        inOrder.verify(mockFtpClient).disconnect();

        String received = new String(contentBytes, 0, contentLength, StandardCharsets.UTF_8);
        assertEquals(received, CONTENT);
    }

    @Test
    public void testPutIOException() throws IOException {
        when(mockFtpClient.isConnected()).thenReturn(true);
        when(mockFtpClient.login(USER, PW)).thenReturn(true);
        doThrow(IOException.class).when(mockFtpClient).storeFile(any(), any());

        IOException exception = null;
        try {
            ftpStore.put(CONTENT, FILENAME);
        } catch (IOException e) {
            exception = e;
        }
        assertNotNull(exception);

        verify(mockFtpClient).disconnect();
    }

    @Test
    public void testPutLoginFails() throws IOException {
        when(mockFtpClient.isConnected()).thenReturn(true);
        when(mockFtpClient.login(USER, PW)).thenReturn(false);

        assertFalse(ftpStore.put(CONTENT, FILENAME));

        verify(mockFtpClient).disconnect();
        verify(mockFtpClient, never()).storeFile(any(), any());
    }
}
