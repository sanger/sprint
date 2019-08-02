package uk.ac.sanger.printy.service.protocol;

import uk.ac.sanger.printy.model.Credentials;
import uk.ac.sanger.printy.model.Printer;

import java.io.IOException;


public class FtpPrintProtocolAdapter implements PrintProtocolAdapter {

    private Printer printer;

    public FtpPrintProtocolAdapter(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void print(String printCode) throws IOException {
        Credentials credentials = printer.getPrinterType().getCredentials();
        if (credentials==null) {
            throw new IllegalArgumentException("No credentials supplied for FTP print");
        }
        FTPStore ftpStore = new FTPStore(printer.getHostname(), credentials.getUsername(), credentials.getPassword());
        if (!ftpStore.put(printCode, "printjob.txt")) {
            throw new IOException("Print failed");
        }
    }

}
