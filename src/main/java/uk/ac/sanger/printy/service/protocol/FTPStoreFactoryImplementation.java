package uk.ac.sanger.printy.service.protocol;

import org.springframework.stereotype.Component;
import uk.ac.sanger.printy.model.Credentials;

/**
 * @author dr6
 */
@Component
public class FTPStoreFactoryImplementation implements FTPStoreFactory {
    @Override
    public FTPStore getFTPStore(String server, Credentials credentials) {
        return new FTPStore(server, credentials.getUsername(), credentials.getPassword());
    }
}
