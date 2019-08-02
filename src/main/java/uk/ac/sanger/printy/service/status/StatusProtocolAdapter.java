package uk.ac.sanger.printy.service.status;

import uk.ac.sanger.printy.model.PrintStatus;

import java.io.IOException;

public interface StatusProtocolAdapter {
    PrintStatus getPrintStatus(String jobId) throws IOException;
}
