package uk.ac.sanger.printy.service.status;

import org.springframework.stereotype.Component;
import uk.ac.sanger.printy.model.Printer;
import uk.ac.sanger.printy.model.StatusProtocol;

@Component
public class StatusProtocolAdapterFactoryImplementation implements StatusProtocolAdapterFactory {
    @Override
    public StatusProtocolAdapter getStatusProtocolAdapter(Printer printer) {
        StatusProtocol statusProtocol = printer.getPrinterType().getStatusProtocol();
        if (statusProtocol ==StatusProtocol.SOAP) {
            return new SoapStatusProtocolAdapter(printer);
        }
        throw new UnsupportedOperationException("Unsupported status protocol: "+statusProtocol);
    }
}
