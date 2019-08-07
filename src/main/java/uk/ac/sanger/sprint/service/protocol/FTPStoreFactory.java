package uk.ac.sanger.sprint.service.protocol;

import uk.ac.sanger.sprint.model.Credentials;

/**
 * Factory for creating an FTPStore
 */
public interface FTPStoreFactory {
    /**
     * Gets an FTP store with the given arguments
     * @param server the server to connect to
     * @param credentials the ftp credentials
     * @return a new FTPStore
     */
    FTPStore getFTPStore(String server, Credentials credentials);
}
