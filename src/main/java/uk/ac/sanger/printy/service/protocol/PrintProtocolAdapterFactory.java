package uk.ac.sanger.printy.service.protocol;

import uk.ac.sanger.printy.model.Printer;

public interface PrintProtocolAdapterFactory {
    PrintProtocolAdapter getPrintProtocolAdapter(Printer printer);
}
