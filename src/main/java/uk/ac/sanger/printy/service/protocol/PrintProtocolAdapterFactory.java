package uk.ac.sanger.printy.service.protocol;

import uk.ac.sanger.printy.model.Printer;

/**
 * Factory to supply protocol adapters.
 */
public interface PrintProtocolAdapterFactory {
    /**
     * Gets a protocol adapter which can send a print request to a given printer.
     * @param printer the printer we want to send a request to
     * @return a protocol adapter for the given printer
     */
    PrintProtocolAdapter getPrintProtocolAdapter(Printer printer);
}
