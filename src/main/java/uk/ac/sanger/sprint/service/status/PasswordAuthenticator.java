package uk.ac.sanger.sprint.service.status;

import uk.ac.sanger.sprint.model.Credentials;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * Authenticator for connecting to an API that uses Basic authentication
 * @author cs24
 */
public class PasswordAuthenticator extends Authenticator {
    private final Credentials credentials;

    public PasswordAuthenticator(Credentials credentials) {
        this.credentials = credentials;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(credentials.getUsername(), credentials.getPassword().toCharArray());
    }
}
