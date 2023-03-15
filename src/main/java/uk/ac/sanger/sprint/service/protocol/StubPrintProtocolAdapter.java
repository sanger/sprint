package uk.ac.sanger.sprint.service.protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author dr6
 */
public class StubPrintProtocolAdapter implements PrintProtocolAdapter {
    static Logger log = LoggerFactory.getLogger(StubPrintProtocolAdapter.class);

    @Override
    public void print(String printCode) throws IOException {
        log.info("Print code: {}", printCode);
    }
}
