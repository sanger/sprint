package uk.ac.sanger.sprint.service.status;

import org.springframework.stereotype.Component;
import uk.ac.sanger.sprint.model.Printer;
import uk.ac.sanger.sprint.model.StatusProtocol;

@Component
public class StatusProtocolAdapterFactoryImplementation implements StatusProtocolAdapterFactory {

    private SoapStatusAuthenticatorFactory soapStatusAuthenticatorFactory;

    public StatusProtocolAdapterFactoryImplementation(SoapStatusAuthenticatorFactory soapStatusAuthenticatorFactory) {
        this.soapStatusAuthenticatorFactory = soapStatusAuthenticatorFactory;
    }

    @Override
    public StatusProtocolAdapter getStatusProtocolAdapter(Printer printer) {
        StatusProtocol statusProtocol = printer.getPrinterType().getStatusProtocol();
        if (statusProtocol == StatusProtocol.SOAP) {
            return new SoapStatusProtocolAdapter(printer, soapStatusAuthenticatorFactory);
        }
        throw new UnsupportedOperationException("Unsupported status protocol: "+statusProtocol);
    }
}
