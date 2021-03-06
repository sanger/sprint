package uk.ac.sanger.sprint.config;

import java.nio.file.Path;
import java.util.Collection;

/**
 * A tool to load the config for the application.
 */
public interface ConfigLoader {
    /**
     * Reads and returns the config for the application.
     * Multiple config files can be read and aggregated into combined config object
     * @param paths the paths to read to get the config
     * @return config including a map to all printers from their hostnames
     */
    Config loadConfig(Collection<Path> paths);
}
