package uk.ac.sanger.printy.config;

import uk.ac.sanger.printy.model.LabelType;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author dr6
 */
@XmlRootElement(name="label_types")
public class LabelTypeConfig {
    private List<LabelType> labelTypes = new ArrayList<>();

    @XmlElement(name="label_type")
    public List<LabelType> getLabelTypes() {
        return this.labelTypes;
    }

    public void setLabelTypes(List<LabelType> labelTypes) {
        this.labelTypes = labelTypes;
    }

    public static LabelTypeConfig load(Path path) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(LabelTypeConfig.class);
        Unmarshaller um = jc.createUnmarshaller();
        return (LabelTypeConfig) um.unmarshal(path.toFile());
    }

    public static Map<String, LabelType> loadLabelTypes(Path path) throws JAXBException {
        LabelTypeConfig ltc = load(path);
        return ltc.getLabelTypes().stream()
                .collect(Collectors.toMap(LabelType::getName, Function.identity()));
    }

}
