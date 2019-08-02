package uk.ac.sanger.printy.service.protocol;

import org.springframework.stereotype.Component;
import uk.ac.sanger.printy.model.Printer;
import uk.ac.sanger.printy.model.Protocol;

@Component
public class PrintProtocolAdapterFactoryImplementation implements PrintProtocolAdapterFactory {
    private final FTPStoreFactory ftpStoreFactory;

    public PrintProtocolAdapterFactoryImplementation(FTPStoreFactory ftpStoreFactory) {
        this.ftpStoreFactory = ftpStoreFactory;
    }

    @Override
    public PrintProtocolAdapter getPrintProtocolAdapter(Printer printer) {
        Protocol protocol = printer.getPrinterType().getProtocol();
        if (protocol == Protocol.FTP) {
            return new FtpPrintProtocolAdapter(printer, ftpStoreFactory);
        }
        throw new UnsupportedOperationException("Unsupported printing protocol: "+protocol);
    }
}
