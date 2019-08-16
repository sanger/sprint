package uk.ac.sanger.sprint.model;

import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * A particular model of printer.
 * Currently we assume that all printers of the same type will use the same
 * language, protocols and credentials (if any).
 */
public class PrinterType {
    private String name;
    private PrinterLanguage language;
    private Protocol protocol;
    private StatusProtocol statusProtocol;

    private Credentials credentials;

    private List<Printer> printers = new ArrayList<>();

    /**
     * Constructs a new printer type with default values for its fields
     */
    public PrinterType() {
    }

    /**
     * Constructs a new printer type with the given values for its fields
     * @param name the name of the printer type
     * @param language the printer language
     * @param protocol the printing protocol
     * @param statusProtocol the protocol for checking the status of a print job
     * @param credentials the credentials for accessing the printer (if any)
     */
    public PrinterType(String name, PrinterLanguage language, Protocol protocol, StatusProtocol statusProtocol,
                       Credentials credentials) {
        this.name = name;
        this.language = language;
        this.protocol = protocol;
        this.statusProtocol = statusProtocol;
        this.credentials = credentials;
    }

    /**
     * Gets the name of this printer type
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this printer type
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the printer language for this printer type
     * @return the printer language
     */
    public PrinterLanguage getLanguage() {
        return language;
    }

    /**
     * Sets the printer language for this printer type
     * @param language the new printer language
     */
    public void setLanguage(PrinterLanguage language) {
        this.language = language;
    }

    /**
     * Gets the printing protocol for this printer type
     * @return the printing protocol
     */
    public Protocol getProtocol() {
        return protocol;
    }

    /**
     * Sets the printing protocol for this printer type
     * @param protocol the new printing protocol
     */
    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    /**
     * Gets the status protocol for this printer type.
     * Not all printer types have any status protocol
     * @return the status protocol, or null
     */
    public StatusProtocol getStatusProtocol() {
        return statusProtocol;
    }

    /**
     * Sets the status protocol for this printer type
     * @param statusProtocol the new status protocol, or null
     */
    public void setStatusProtocol(StatusProtocol statusProtocol) {
        this.statusProtocol = statusProtocol;
    }

    /**
     * Gets the credentials for this printer type.
     * Not all printer types require credentials
     * @return the credentials, or null
     */
    public Credentials getCredentials() {
        return credentials;
    }

    /**
     * Sets the credentials for this printer type
     * @param credentials the new credentials, or null
     */
    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    /**
     * Gets the list of printers of this type.
     * By default this is an {@link ArrayList}, so you can add things to this list directly
     * @return the list of printers of this type
     */
    public List<Printer> getPrinters() {
        return printers;
    }

    /**
     * Sets the list of printers of this type
     * @param printers the list of printers of this type
     */
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
