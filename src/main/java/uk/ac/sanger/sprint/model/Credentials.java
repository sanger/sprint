package uk.ac.sanger.sprint.model;

/**
 * Login credentials for a web service.
 * Not all services require credentials.
 */
public class Credentials {
    private String username;
    private String password;

    /**
     * Constructs credentials with null for username and password.
     */
    public Credentials() {}

    /**
     * Constructs credentials with the given username and password
     * @param username the username
     * @param password the password
     */
    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the username
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
