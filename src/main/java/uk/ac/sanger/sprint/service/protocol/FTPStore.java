package uk.ac.sanger.sprint.service.protocol;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

public class FTPStore {
    private static Logger log = LoggerFactory.getLogger(FTPStore.class);

    private String server, username, password, ipAddress;
    private int timeout = 10*1000; // 10 s
    private Supplier<FTPClient> ftpClientSupplier = FTPClient::new;

    public FTPStore(String server, String username, String password, String ipAddress) {
        this.server = server;
        this.username = username;
        this.password = password;
        this.ipAddress = ipAddress;
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
                log.info("FTP: failed to connect to {}", server);
                return false;
            }
            log.debug("entering passive mode");
            ftp.enterLocalActiveMode();
            if (ipAddress!=null) {
                log.debug("setting report active external ip address {}", ipAddress);
                ftp.setReportActiveExternalIPAddress(ipAddress);
            }
            log.debug("logging in");
            if (!ftp.login(username, password)) {
                log.info("FTP: failed to log in to {}", server);
                return false;
            }
            log.debug("storing file");
            try (InputStream fis = makeInputStream(content)) {
                if (!ftp.storeFile(filename, fis)) {
                    log.info("FTP: failed to store file on {}", server);
                    return false;
                }
            }
            log.info("FTP: file sent to {}", server);
        } finally {
            if (ftp.isConnected()) {
                ftp.disconnect();
            }
        }
        return true;
    }
}
