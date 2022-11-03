package at.jku.cps.travart.plugin.ovm.ovm.io;

import at.jku.cps.travart.core.common.IReader;
import at.jku.cps.travart.core.exception.NotSupportedVariabilityTypeException;
import at.jku.cps.travart.plugin.ovm.ovm.factory.impl.OvModelFactory;
import at.jku.cps.travart.plugin.ovm.ovm.format.impl.ReadHelper;
import at.jku.cps.travart.plugin.ovm.ovm.format.impl.exc.OvModelSerialisationException;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModel;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;

public class OvModelReader implements IReader<IOvModel> {

    private final OvModelFactory factory;

    public OvModelReader() {
        this.factory = OvModelFactory.getInstance();
    }

    @Override
    public IOvModel read(Path path) throws IOException, NotSupportedVariabilityTypeException {
        try {
            IOvModel ovModel = this.factory.create();
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(path.toFile());
            ovModel = ReadHelper.readModel(document, ovModel, this.factory);
            return ovModel;
        } catch (OvModelSerialisationException | SAXException | ParserConfigurationException e) {
            throw new IOException(e);
        }
    }
}
