package uk.ac.sanger.sprint.service.protocol;

import java.io.IOException;

/**
 * @author dr6
 */
public class StubPrintProtocolAdapter implements PrintProtocolAdapter {
    @Override
    public void print(String printCode) throws IOException {
        // Nothing happens
    }
}
