package uk.ac.sanger.printy.service.protocol;

import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FTPStore {
    private String server, username, password;
    private int timeout = 10*1000; // 10 s

    public FTPStore(String server, String username, String password) {
        this.server = server;
        this.username = username;
        this.password = password;
    }

    /**
     * Sets the timeout to use for ftp connections, in milliseconds
     * @param timeout the timeout to use for ftp connections
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    private InputStream makeInputStream(String content) {
        return new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
    }

    public boolean put(String content, String filename) throws IOException {
        FTPClient ftp = new FTPClient();
        try {
            ftp.setConnectTimeout(timeout);
            ftp.setDataTimeout(timeout);
            ftp.setDefaultTimeout(timeout);
            ftp.connect(server);
            if (!ftp.isConnected()) {
                return false;
            }
            ftp.enterLocalPassiveMode();
            if (!ftp.login(username, password)) {
                return false;
            }
            try (InputStream fis = makeInputStream(content)) {
                if (!ftp.storeFile(filename, fis)) {
                    return false;
                }
            }
        } finally {
            if (ftp.isConnected()) {
                ftp.disconnect();
            }
        }
        return true;
    }
}
