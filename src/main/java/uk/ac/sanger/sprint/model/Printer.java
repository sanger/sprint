package uk.ac.sanger.sprint.model;

import com.google.common.base.MoreObjects;

public class Printer {
    private String hostname;
    private LabelType labelType;
    private PrinterType printerType;

    public Printer() {}

    public Printer(String hostname, PrinterType printerType, LabelType labelType) {
        this.hostname = hostname;
        this.printerType = printerType;
        this.labelType = labelType;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public PrinterType getPrinterType() {
        return printerType;
    }

    public void setPrinterType(PrinterType printerType) {
        this.printerType = printerType;
    }

    public LabelType getLabelType() {
        return labelType;
    }

    public void setLabelType(LabelType labelType) {
        this.labelType = labelType;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("hostname", hostname)
                .add("labelType", labelType)
                .add("printerType", printerType)
                .toString();
    }
}
