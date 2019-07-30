package uk.ac.sanger.printy.service;

import java.io.IOException;

public interface PrintProtocolAdapter {

    void print(String printCode) throws IOException;
}
