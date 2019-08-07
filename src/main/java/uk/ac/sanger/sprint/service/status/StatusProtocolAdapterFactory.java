package uk.ac.sanger.sprint.service.status;

import uk.ac.sanger.sprint.model.Printer;

/**
 * Factory for getting status protocol adapters.
 */
public interface StatusProtocolAdapterFactory {
    /**
     * Gets a status protocol adapter for the given printer.
     * Not all printers support getting a job status.
     * @param printer the printer to contact
     * @return a status adapter for the given printer
     * @exception UnsupportedOperationException if status requests are not supported for the given printer
     */
    StatusProtocolAdapter getStatusProtocolAdapter(Printer printer);
}
