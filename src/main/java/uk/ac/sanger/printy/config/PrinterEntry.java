package uk.ac.sanger.printy.config;

/**
 * Class representing the normalised printer info stored in config
 * @author dr6
 */
class PrinterEntry {
    private String hostname;
    private String labelType;
    private String printerType;

    public String getHostname() {
        return this.hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getLabelType() {
        return this.labelType;
    }

    public void setLabelType(String labelType) {
        this.labelType = labelType;
    }

    public String getPrinterType() {
        return this.printerType;
    }

    public void setPrinterType(String printerType) {
        this.printerType = printerType;
    }
}
