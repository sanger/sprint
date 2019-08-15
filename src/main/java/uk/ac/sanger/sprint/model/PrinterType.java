package uk.ac.sanger.sprint.model;

import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.List;

public class PrinterType {
    private String name;
    private PrinterLanguage language;
    private Protocol protocol;
    private StatusProtocol statusProtocol;

    private Credentials credentials;

    private List<Printer> printers = new ArrayList<>();

    public PrinterType() {
    }

    public PrinterType(String name, PrinterLanguage language, Protocol protocol, StatusProtocol statusProtocol,
                       Credentials credentials) {
        this.name = name;
        this.language = language;
        this.protocol = protocol;
        this.statusProtocol = statusProtocol;
        this.credentials = credentials;
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

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public List<Printer> getPrinters() {
        return printers;
    }

    public void setPrinters(List<Printer> printers) {
        this.printers = printers;
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
