package uk.ac.sanger.sprint.service.protocol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;
import uk.ac.sanger.sprint.model.Printer;
import uk.ac.sanger.sprint.model.Protocol;
import uk.ac.sanger.sprint.utils.UniqueInstantSupplier;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;

@Component
public class PrintProtocolAdapterFactoryImplementation implements PrintProtocolAdapterFactory {
    private final FTPStoreFactory ftpStoreFactory;
    private final UniqueInstantSupplier uniqueInstantSupplier;
    private final String ipAddress;
    private final DateTimeFormatter timeFormat;

    @Autowired
    public PrintProtocolAdapterFactoryImplementation(FTPStoreFactory ftpStoreFactory,
                                                     ApplicationArguments arguments,
                                                     UniqueInstantSupplier uniqueInstantSupplier) {
        this.ftpStoreFactory = ftpStoreFactory;
        this.uniqueInstantSupplier = uniqueInstantSupplier;
        List<String> ipAddressArg = arguments.getOptionValues("ipAddress");
        if (ipAddressArg!=null && !ipAddressArg.isEmpty()) {
            this.ipAddress = ipAddressArg.get(0);
        } else {
            this.ipAddress = null;
        }
        this.timeFormat = makeDateTimeFormatter();
    }

    public static DateTimeFormatter makeDateTimeFormatter() {
        return new DateTimeFormatterBuilder()
                .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                .appendLiteral(".csv")
                .toFormatter();
    }

    @Override
    public PrintProtocolAdapter getPrintProtocolAdapter(Printer printer) {
        Protocol protocol = printer.getPrinterType().getProtocol();
        if (protocol!=null) {
            switch (protocol) {
                case FTP: return new FtpPrintProtocolAdapter(printer, ftpStoreFactory, ipAddress);
                case VOLUME: return new VolumeProtocolAdapter(printer, timeFormat, uniqueInstantSupplier);
                case STUB: return new StubPrintProtocolAdapter();
            }
        }
        throw new UnsupportedOperationException("Unsupported printing protocol: "+protocol);
    }
}
