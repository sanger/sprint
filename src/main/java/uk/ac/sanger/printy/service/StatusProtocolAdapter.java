package uk.ac.sanger.printy.service;

import uk.ac.sanger.printy.model.PrintStatus;

public interface StatusProtocolAdapter {
    PrintStatus getStatus(String jobId);
}
