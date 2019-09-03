package uk.ac.sanger.sprint.service.protocol;

import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;
import uk.ac.sanger.sprint.model.Printer;
import uk.ac.sanger.sprint.model.Protocol;

import java.util.List;

@Component
public class PrintProtocolAdapterFactoryImplementation implements PrintProtocolAdapterFactory {
    private final FTPStoreFactory ftpStoreFactory;
    private final String ipAddress;

    public PrintProtocolAdapterFactoryImplementation(FTPStoreFactory ftpStoreFactory,
                                                     ApplicationArguments arguments) {
        this.ftpStoreFactory = ftpStoreFactory;
        List<String> ipAddressArg = arguments.getOptionValues("ipAddress");
        if (ipAddressArg!=null && !ipAddressArg.isEmpty()) {
            this.ipAddress = ipAddressArg.get(0);
        } else {
            this.ipAddress = null;
        }
    }

    @Override
    public PrintProtocolAdapter getPrintProtocolAdapter(Printer printer) {
        Protocol protocol = printer.getPrinterType().getProtocol();
        if (protocol!=null) {
            switch (protocol) {
                case FTP: return new FtpPrintProtocolAdapter(printer, ftpStoreFactory, ipAddress);
                case STUB: return new StubPrintProtocolAdapter();
            }
        }
        throw new UnsupportedOperationException("Unsupported printing protocol: "+protocol);
    }
}
