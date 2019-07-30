package uk.ac.sanger.printy.model;

import com.google.common.base.MoreObjects;

public class Printer {
    private String name;
    private PrinterLanguage language;
    private Protocol protocol;
    private StatusProtocol statusProtocol;

    public Printer() {}

    public Printer(String name, PrinterLanguage language, Protocol protocol, StatusProtocol statusProtocol) {
        this.name = name;
        this.language = language;
        this.protocol = protocol;
        this.statusProtocol = statusProtocol;
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("language", language)
                .add("protocol", protocol)
                .add("statusProtocol", statusProtocol)
                .toString();
    }
}
