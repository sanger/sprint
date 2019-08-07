package uk.ac.sanger.sprint.service.protocol;

import uk.ac.sanger.sprint.service.language.PrinterLanguageAdapter;

import java.io.IOException;

/**
 * An adapter to send a print request to a particular printer.
 */
public interface PrintProtocolAdapter {

    /**
     * Sends a print request.
     * The print code is expected to come from a suitable {@link PrinterLanguageAdapter}.
     * @param printCode the code representing the request
     * @exception IOException a communication problem
     */
    void print(String printCode) throws IOException;

}
