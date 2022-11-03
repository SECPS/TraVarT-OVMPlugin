package at.jku.cps.travart.plugin.ovm.ovm.io;

import at.jku.cps.travart.core.common.IWriter;
import at.jku.cps.travart.core.exception.NotSupportedVariabilityTypeException;
import at.jku.cps.travart.plugin.ovm.ovm.format.impl.WriteHelper;
import at.jku.cps.travart.plugin.ovm.ovm.format.impl.exc.OvModelSerialisationException;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModel;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class OvModelWriter implements IWriter<IOvModel> {

    public static final String OVM_FILE_EXTENSION = ".ovm";

    private static final String INDENT_CONSTANT = "{http://xml.apache.org/xslt}indent-amount";
    private static final String DEFAULT_INDET_NUMBER = "4";

    @Override
    public void write(IOvModel ovModel, Path path) throws IOException, NotSupportedVariabilityTypeException {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            WriteHelper.writeModel(ovModel, document);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(INDENT_CONSTANT, DEFAULT_INDET_NUMBER);
            DOMSource source = new DOMSource(document);
            FileWriter writer = new FileWriter(path.toFile(), StandardCharsets.UTF_8);
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | OvModelSerialisationException | TransformerFactoryConfigurationError
                | TransformerException e) {
            throw new IOException(e);
        }
    }
}
