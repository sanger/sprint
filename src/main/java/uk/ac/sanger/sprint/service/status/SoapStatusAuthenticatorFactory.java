package uk.ac.sanger.sprint.service.status;

import uk.ac.sanger.sprint.model.Credentials;

import java.net.Authenticator;

/**
 * @author cs24
 */
public interface SoapStatusAuthenticatorFactory {

    /**
     * Returns an Authenticator for retrieving the status of print requests using SOAP
     * @param credentials for authenticating with the SOAP service
     * @return an Authenticator
     */
    Authenticator getAuthenticator(Credentials credentials);
}
