package uk.ac.sanger.sprint.service.status;

import org.springframework.stereotype.Component;
import uk.ac.sanger.sprint.model.Credentials;

import java.net.Authenticator;

/**
 * @author cs24
 */
@Component
public class SoapStatusAuthenticatorFactoryImplementation implements SoapStatusAuthenticatorFactory {

    @Override
    public Authenticator getAuthenticator(Credentials credentials) {
        return new PasswordAuthenticator(credentials);
    }

}

