package uk.ac.sanger.printy.service.protocol;

import uk.ac.sanger.printy.model.Printer;

public class LpdPrintProtocolAdapter implements PrintProtocolAdapter {

    private Printer printer;

    public LpdPrintProtocolAdapter(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void print(String printCode) {
        // todo
    }

}
