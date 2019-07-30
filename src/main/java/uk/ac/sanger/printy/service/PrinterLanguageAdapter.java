package uk.ac.sanger.printy.service;

import uk.ac.sanger.printy.model.PrintRequest;

public interface PrinterLanguageAdapter {
    String transcribe(PrintRequest request, String jobId);
}
