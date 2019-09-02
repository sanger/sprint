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

    public FtpPrintProtocolAdapter(Printer printer, FTPStoreFactory ftpStoreFactory) {
        this.printer = printer;
        this.ftpStoreFactory = ftpStoreFactory;
    }

    @Override
    public void print(String printCode) throws IOException {
        Credentials credentials = printer.getPrinterType().getCredentials();
        if (credentials==null) {
            throw new IllegalArgumentException("No credentials supplied for FTP print");
        }
        FTPStore ftpStore = ftpStoreFactory.getFTPStore(printer.getAddress(), credentials);
        if (!ftpStore.put(printCode, "printjob.txt")) {
            throw new IOException("Print failed");
        }
    }

}
