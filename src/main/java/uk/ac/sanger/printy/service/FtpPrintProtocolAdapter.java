package uk.ac.sanger.printy.service;

import uk.ac.sanger.printy.model.Printer;

import java.io.IOException;


public class FtpPrintProtocolAdapter implements PrintProtocolAdapter {

    private Printer printer;

    public FtpPrintProtocolAdapter(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void print(String printCode) throws IOException {
        FTPStore ftpStore = new FTPStore(printer.getHostname(), "ftpprint", "print");
        if (!ftpStore.put(printCode, "printjob.txt")) {
            throw new IOException("Print failed");
        }
    }
}
