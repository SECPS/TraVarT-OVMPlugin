package at.jku.cps.travart.plugin.ovm.ovm.format.impl;

import de.ovgu.featureide.fm.core.io.xml.XmlFeatureModelFormat;
import org.w3c.dom.Element;

/**
 * This class represents an extension of the {@link XmlFeatureModelFormat}. It
 * is used to store the properties of a feature model precisely by using the
 * provided methods of the base class. These properties are for example the
 * location of the file menu.
 *
 * @author johannstoebich
 */
public class PropertyHelper extends XmlFeatureModelFormat {

    private static PropertyHelper instance;

    private PropertyHelper() {

    }

    /**
     * This method returns the singelton instance of the PropertyHelper.
     *
     * @return the instance.
     */
    public static PropertyHelper getSingeltonInstance() {
        if (instance == null) {
            instance = new PropertyHelper();
        }
        return instance;
    }

    /**
     * This method checks if the XML-{@link Element} contains feature model
     * properties. This properties can be separated into GRAPHICS, CALCULATIONS and
     * PROPERTY properties.
     *
     * @param element the XML-{@link Element} containing the properties.
     */
    public boolean isFeatureModelProperties(final Element element) {
        final String nodeName = element.getNodeName();
        switch (nodeName) {
            case GRAPHICS:
            case CALCULATIONS:
            case PROPERTY:
                return true;
            default:
                return false;
        }
    }
}
