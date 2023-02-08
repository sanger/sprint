package uk.ac.sanger.sprint.config;

/**
 * Class representing the unnested printer info stored in config.
 * The printer class itself contains references to a LabelType and a PrinterType.
 * This is the deserialisable version that instead contains the names
 * of a label type and a printer type, which are resolved when the config is processed.
 * @author dr6
 */
class PrinterEntry {
    private String hostname;
    private String labelType;
    private String printerType;
    private String path;

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

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
