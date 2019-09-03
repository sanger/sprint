package uk.ac.sanger.sprint.service.protocol;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

public class FTPStore {
    private static Logger log = LoggerFactory.getLogger(FTPStore.class);

    private String server, username, password;
    private int timeout = 10*1000; // 10 s
    private Supplier<FTPClient> ftpClientSupplier = FTPClient::new;

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

    public void setFtpClientSupplier(Supplier<FTPClient> ftpClientSupplier) {
        this.ftpClientSupplier = ftpClientSupplier;
    }

    private InputStream makeInputStream(String content) {
        return new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
    }

    public boolean put(String content, String filename) throws IOException {
        FTPClient ftp = ftpClientSupplier.get();
        try {
            ftp.setConnectTimeout(timeout);
            ftp.setDataTimeout(timeout);
            ftp.setDefaultTimeout(timeout);
            log.debug("connecting");
            ftp.connect(server);
            if (!ftp.isConnected()) {
                log.info("Failed to connect");
                return false;
            }
            log.debug("entering passive mode");
            ftp.enterLocalPassiveMode();
            log.debug("logging in");
            if (!ftp.login(username, password)) {
                log.info("Failed to log in");
                return false;
            }
            log.debug("storing file");
            try (InputStream fis = makeInputStream(content)) {
                if (!ftp.storeFile(filename, fis)) {
                    log.info("Failed to store file");
                    return false;
                }
            }
            log.info("File sent");
        } finally {
            if (ftp.isConnected()) {
                ftp.disconnect();
            }
        }
        return true;
    }
}
