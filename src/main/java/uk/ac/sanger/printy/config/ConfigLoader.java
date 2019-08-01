package uk.ac.sanger.printy.config;

import uk.ac.sanger.printy.model.Printer;

import java.nio.file.Path;
import java.util.Map;

public interface ConfigLoader {
    Map<String, Printer> getPrinters(Path path);
}
