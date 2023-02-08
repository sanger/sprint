package uk.ac.sanger.sprint.model;

/**
 * The protocol used to send a print request
 */
public enum Protocol {
    /** Send over FTP */
    FTP,
    /** Copy to a mounted volume */
    VOLUME,
    /** Validate the request */
    STUB,
}
