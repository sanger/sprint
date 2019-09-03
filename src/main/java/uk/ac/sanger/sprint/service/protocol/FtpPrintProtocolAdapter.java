package uk.ac.sanger.sprint.service.protocol;

import uk.ac.sanger.sprint.model.Credentials;
import uk.ac.sanger.sprint.model.Printer;

import java.io.IOException;

/**
 * An adapter for sending a print request to a particular printer over FTP.
 */
public class FtpPrintProtocolAdapter implements PrintProtocolAdapter {

    private Printer printer;
    private FTPStoreFactory ftpStoreFactory;
    private String ipAddress;

    public FtpPrintProtocolAdapter(Printer printer, FTPStoreFactory ftpStoreFactory, String ipAddress) {
        this.printer = printer;
        this.ftpStoreFactory = ftpStoreFactory;
        this.ipAddress = ipAddress;
    }

    @Override
    public void print(String printCode) throws IOException {
        Credentials credentials = printer.getPrinterType().getCredentials();
        if (credentials==null) {
            throw new IllegalArgumentException("No credentials supplied for FTP print");
        }
        FTPStore ftpStore = ftpStoreFactory.getFTPStore(printer.getAddress(), credentials, ipAddress);
        if (!ftpStore.put(printCode, "printjob.txt")) {
            throw new IOException("Print failed");
        }
    }

}
