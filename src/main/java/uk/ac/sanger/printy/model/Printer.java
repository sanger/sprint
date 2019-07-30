package uk.ac.sanger.printy.model;

import com.google.common.base.MoreObjects;

public class Printer {
    private String name;
    private PrinterLanguage language;
    private Protocol protocol;
    private StatusProtocol statusProtocol;
    private LabelType labelType;

    public Printer() {}

    public Printer(String name, PrinterLanguage language, Protocol protocol, StatusProtocol statusProtocol,
                   LabelType labelType) {
        this.name = name;
        this.language = language;
        this.protocol = protocol;
        this.statusProtocol = statusProtocol;
        this.labelType = labelType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PrinterLanguage getLanguage() {
        return language;
    }

    public void setLanguage(PrinterLanguage language) {
        this.language = language;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public StatusProtocol getStatusProtocol() {
        return statusProtocol;
    }

    public void setStatusProtocol(StatusProtocol statusProtocol) {
        this.statusProtocol = statusProtocol;
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
                .add("name", name)
                .add("language", language)
                .add("protocol", protocol)
                .add("statusProtocol", statusProtocol)
                .add("labelType", labelType.getName())
                .toString();
    }
}
