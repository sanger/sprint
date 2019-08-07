package uk.ac.sanger.sprint.service.language;

import uk.ac.sanger.sprint.model.PrintRequest;

/**
 * An adapter to create printer language for a particular printer.
 */
public interface PrinterLanguageAdapter {
    /**
     * Creates a string representing the given print request.
     * @param request the print request to transcribe
     * @param jobId the id of the job
     * @return a string suitable to be sent to the printer
     */
    String transcribe(PrintRequest request, String jobId);
}
