package uk.ac.sanger.sprint.service.protocol;

import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Tests for the {@link StubPrintProtocolAdapter}
 * @author dr6
 */

public class StubPrintProtocolAdapterTest {
    @Test
    public void testPrint() throws IOException {
        StubPrintProtocolAdapter adapter = new StubPrintProtocolAdapter();
        adapter.print("bananas");
        // throws no exception; does nothing
    }
}
