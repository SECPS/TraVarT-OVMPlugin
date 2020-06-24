package de.ovgu.featureide.core.ovm.format.impl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.ovgu.featureide.fm.core.base.IPropertyContainer;
import de.ovgu.featureide.fm.core.io.xml.XmlFeatureModelFormat;

/**
 * TODO description
 *
 * @author johannstoebich
 */
public class PropertyHelper extends XmlFeatureModelFormat {

	private static PropertyHelper instance;

	private PropertyHelper() {

	}

	public static PropertyHelper getSingeltonInstance() {
		if (instance == null) {
			instance = new PropertyHelper();
		}
		return instance;
	}

	public static void writeProperties(Document doc, IPropertyContainer properties, Element fnod) {
		getSingeltonInstance().addProperties(doc, properties, fnod);
	}

	public static void readProperties(IPropertyContainer properties, Element e) {
		getSingeltonInstance().parseFeatureModelProperties(properties, e);
	}

	public boolean isFeatureModelProperties(Element e) {
		final String nodeName = e.getNodeName();
		switch (nodeName) {
		case GRAPHICS:
		case CALCULATIONS:
		case PROPERTY:
			return true;
		default:
			return false;
		}
	}

	protected void parseFeatureModelProperties(IPropertyContainer properties, Element e) {
		for (final Element propertyElement : getElements(e.getChildNodes())) {
			final String nodeName = propertyElement.getNodeName();
			switch (nodeName) {
			case GRAPHICS:
				parseProperty(properties, propertyElement, GRAPHICS);
				break;
			case CALCULATIONS:
				parseProperty(properties, propertyElement, CALCULATIONS);
				break;
			case PROPERTY:
				parseProperty(properties, propertyElement, null);
				break;
			}
		}
	}
}
