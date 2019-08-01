package uk.ac.sanger.printy.service;

import uk.ac.sanger.printy.model.Printer;

public interface StatusProtocolAdapterFactory {
    StatusProtocolAdapter getStatusProtocolAdapter(Printer printer);
}
