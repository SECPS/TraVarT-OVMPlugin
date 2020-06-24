package de.ovgu.featureide.core.ovm.format.impl;

import static de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistanceProperties.ABSTRACT;
import static de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistanceProperties.ALTERNATIVE;
import static de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistanceProperties.DESCRIPTION;
import static de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistanceProperties.HIDDEN;
import static de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistanceProperties.MANDATORY_CHILDREN;
import static de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistanceProperties.MAX_CHOICES;
import static de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistanceProperties.MIN_CHOICES;
import static de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistanceProperties.NAME;
import static de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistanceProperties.OPTIONAL;
import static de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistanceProperties.OPTIONAL_CHILDREN;
import static de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistanceProperties.OV_MODEL;
import static de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistanceProperties.OV_MODEL_CONSTRAINT_METAINFORMATION;
import static de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistanceProperties.OV_MODEL_EXCLUDES_CONSTRAINT;
import static de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistanceProperties.OV_MODEL_EXCLUDES_CONSTRAINT_REFERENCE;
import static de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistanceProperties.OV_MODEL_METAINFORMATION;
import static de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistanceProperties.OV_MODEL_REQUIRES_CONSTRAINT;
import static de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistanceProperties.OV_MODEL_REQUIRES_CONSTRAINT_REFERENCE;
import static de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistanceProperties.OV_MODEL_VARIANT;
import static de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistanceProperties.OV_MODEL_VARIANT_REFERENCE;
import static de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistanceProperties.OV_MODEL_VARIATION_BASE_METAINFORMATION;
import static de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistanceProperties.OV_MODEL_VARIATION_POINT;
import static de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistanceProperties.OV_MODEL_VARIATION_POINT_REFERENCE;
import static de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistanceProperties.PART_OF_OVMODEL_ROOT;
import static de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistanceProperties.REFERENCED_CONSTRAINTS;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import de.ovgu.featureide.core.ovm.base.impl.OvModelUtils;
import de.ovgu.featureide.core.ovm.factory.IOvModelFactory;
import de.ovgu.featureide.core.ovm.format.impl.exception.OvModelSerialisationException;
import de.ovgu.featureide.core.ovm.format.impl.exception.OvModelWrongCountOfElements;
import de.ovgu.featureide.core.ovm.format.impl.exception.OvModelWrongElementException;
import de.ovgu.featureide.core.ovm.model.IIdentifyable;
import de.ovgu.featureide.core.ovm.model.IOvModel;
import de.ovgu.featureide.core.ovm.model.IOvModelElement;
import de.ovgu.featureide.core.ovm.model.IOvModelMetainformation;
import de.ovgu.featureide.core.ovm.model.IOvModelVariant;
import de.ovgu.featureide.core.ovm.model.IOvModelVariationBase;
import de.ovgu.featureide.core.ovm.model.IOvModelVariationBaseMetainformation;
import de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint;
import de.ovgu.featureide.core.ovm.model.constraint.IOvModelConstraint;
import de.ovgu.featureide.core.ovm.model.constraint.IOvModelConstraintMetainformation;
import de.ovgu.featureide.core.ovm.model.constraint.IOvModelExcludesConstraint;
import de.ovgu.featureide.core.ovm.model.constraint.IOvModelRequiresConstraint;

/**
 * TODO description
 *
 * @author johannstoebich
 */
public class ReadHelper {

	public static IOvModel readModel(Document document, IOvModel ovModel, IOvModelFactory factory) throws OvModelSerialisationException {
		final NodeList nodes = document.getElementsByTagName(OV_MODEL);
		if ((nodes == null) || (nodes.getLength() != 1)) {
			throw new OvModelWrongElementException(OV_MODEL);
		}
		final Node node = nodes.item(0);
		if (!(node instanceof Element)) {
			throw new OvModelWrongElementException(node.getNodeName());
		}

		readProperties((Element) node, ovModel, ovModel, factory);

		final Element metainformation = getChildElementByTagName((Element) node, OV_MODEL_METAINFORMATION, true);
		read(metainformation, ovModel.getMetainformation(), ovModel, factory);

		for (final Element element : getChildElements((Element) node, true)) {
			if (PropertyHelper.getSingeltonInstance().isFeatureModelProperties(element)) {
				continue;
			}
			if (element.getNodeName().contentEquals(OV_MODEL_METAINFORMATION)) {
				continue;
			}

			final IOvModelElement ovModelElement = read(element, ovModel, factory);
			if (ovModelElement instanceof IOvModelConstraint) {
				OvModelUtils.addConstraint(ovModel, (IOvModelConstraint) ovModelElement);
			} else {
				OvModelUtils.addVariationPoint(ovModel, (IOvModelVariationPoint) ovModelElement);
			}
		}

		return ovModel;
	}

	private static IOvModelElement read(Element node, IOvModel ovModel, IOvModelFactory factory) throws OvModelSerialisationException {
		final String type = node.getNodeName();
		final String name = node.getAttribute(NAME);

		if (type.equals(OV_MODEL_VARIANT)) {
			final IOvModelVariant ovModelVariant = factory.createVariant(ovModel, name);
			read(node, ovModelVariant, ovModel, factory);
			return ovModelVariant;
		} else if (type.equals(OV_MODEL_VARIATION_POINT)) {
			final IOvModelVariationPoint ovModelVariationPoint = factory.createVariationPoint(ovModel, name);
			read(node, ovModelVariationPoint, ovModel, factory);
			return ovModelVariationPoint;
		} else if (type.equals(OV_MODEL_EXCLUDES_CONSTRAINT)) {
			final IOvModelExcludesConstraint ovModelExcludesConstraint = factory.createExcludesConstraint(ovModel);
			read(node, ovModelExcludesConstraint, ovModel, factory);
			return ovModelExcludesConstraint;
		} else if (type.equals(OV_MODEL_REQUIRES_CONSTRAINT)) {
			final IOvModelRequiresConstraint ovModelRequiresConstraint = factory.createRequiresConstraint(ovModel);
			read(node, ovModelRequiresConstraint, ovModel, factory);
			return ovModelRequiresConstraint;
		} else if (type.equals(OV_MODEL_VARIANT_REFERENCE) || type.equals(OV_MODEL_VARIATION_POINT_REFERENCE)
			|| type.equals(OV_MODEL_EXCLUDES_CONSTRAINT_REFERENCE) || type.equals(OV_MODEL_REQUIRES_CONSTRAINT_REFERENCE)) {
				final IOvModelElement element = ovModel.getElement(factory.createIdentifyable(0, name));
				if (element == null) {
					throw new OvModelWrongElementException(type, name);
				}
				return element;
			} else {
				throw new OvModelWrongElementException(type);
			}
	}

	public static void read(Element node, IOvModelVariant object, IOvModel ovModel, IOvModelFactory factory) throws OvModelSerialisationException {
		readProperties(node, (IOvModelVariationBase) object, ovModel, factory);
	}

	public static void read(Element node, IOvModelVariationPoint object, IOvModel ovModel, IOvModelFactory factory) throws OvModelSerialisationException {
		readProperties(node, (IOvModelVariationBase) object, ovModel, factory);

		final String alternative = node.getAttribute(ALTERNATIVE);
		if ((alternative != null) && alternative.contentEquals(String.valueOf(true))) {
			OvModelUtils.setAlternative(object, true);
		} else {
			OvModelUtils.setAlternative(object, false);
		}

		final String minChoices = node.getAttribute(MIN_CHOICES);
		if ((minChoices != null)) {
			OvModelUtils.setMinChoices(object, Integer.valueOf(minChoices));
		}

		final String maxChoices = node.getAttribute(MAX_CHOICES);
		if ((maxChoices != null)) {
			OvModelUtils.setMaxChoices(object, Integer.valueOf(maxChoices));
		}

		final Element ovMandatoryChildren = getChildElementByTagName(node, MANDATORY_CHILDREN, false);
		if (ovMandatoryChildren != null) {
			for (final Element element : getChildElements(ovMandatoryChildren, true)) {
				final IOvModelVariationBase ovModelVariationBase = (IOvModelVariationBase) read(element, ovModel, factory);
				object.addMandatoryChild(ovModelVariationBase);
			}
		}

		final Element ovOptionalChildren = getChildElementByTagName(node, OPTIONAL_CHILDREN, false);
		if (ovOptionalChildren != null) {
			for (final Element element : getChildElements(ovOptionalChildren, true)) {
				final IOvModelVariationBase ovModelVariationBase = (IOvModelVariationBase) read(element, ovModel, factory);
				object.addOptionalChild(ovModelVariationBase);
			}
		}
	}

	public static void read(Element node, IOvModelMetainformation object, IOvModel ovModel, IOvModelFactory factory) {
		final String description = node.getAttribute(DESCRIPTION);
		object.setDescription(description);

		PropertyHelper.readProperties(object.getCustomProperties(), node);
	}

	public static void read(Element node, IOvModelVariationBaseMetainformation object, IOvModel ovModel, IOvModelFactory factory)
			throws OvModelSerialisationException {
		final String abstractStr = node.getAttribute(ABSTRACT);
		if ((abstractStr != null) && abstractStr.contentEquals(String.valueOf(true))) {
			object.setAbstract(true);
		} else {
			object.setAbstract(false);
		}

		final String hidden = node.getAttribute(HIDDEN);
		if ((hidden != null) && hidden.contentEquals(String.valueOf(true))) {
			object.setHidden(true);
		} else {
			object.setHidden(false);
		}

		final String partOfOvModelRoot = node.getAttribute(PART_OF_OVMODEL_ROOT);
		if ((partOfOvModelRoot != null) && partOfOvModelRoot.contentEquals(String.valueOf(true))) {
			object.setPartOfOvModelRoot(true);
		} else {
			object.setPartOfOvModelRoot(false);
		}

		final String description = node.getAttribute(DESCRIPTION);
		object.setDescription(description);

		final Element referencedConstraints = getChildElementByTagName(node, REFERENCED_CONSTRAINTS, false);
		if (referencedConstraints != null) {
			for (final Element element : getChildElements((Element) referencedConstraints, true)) {
				final IOvModelElement constraint = read(element, ovModel, factory);
				if (!(constraint instanceof IOvModelConstraint)) {
					throw new OvModelWrongElementException(element.getTagName(), element.getNodeName());
				}
				object.getReferencedConstraints().add((IOvModelConstraint) constraint);
			}
		}

		PropertyHelper.readProperties(object.getCustomProperties(), node);
	}

	public static void read(Element node, IOvModelConstraintMetainformation object, IOvModel ovModel, IOvModelFactory factory) {
		final String description = node.getAttribute(DESCRIPTION);
		object.setDescription(description);

		PropertyHelper.readProperties(object.getCustomProperties(), node);
	}

	public static void read(Element node, IOvModelExcludesConstraint object, IOvModel ovModel, IOvModelFactory factory) throws OvModelSerialisationException {
		readProperties(node, object, ovModel, factory);
	}

	public static void read(Element node, IOvModelRequiresConstraint object, IOvModel ovModel, IOvModelFactory factory) throws OvModelSerialisationException {
		readProperties(node, object, ovModel, factory);
	}

	public static void readProperties(Element node, IIdentifyable object, IOvModel ovModel, IOvModelFactory factory) {
		OvModelUtils.setName(object, node.getAttribute(NAME));
	}

	public static void readProperties(Element node, IOvModel object, IOvModel ovModel, IOvModelFactory factory) {
		readProperties(node, (IIdentifyable) object, ovModel, factory);
	}

	public static void readProperties(Element node, IOvModelElement object, IOvModel ovModel, IOvModelFactory factory) {
		readProperties(node, (IIdentifyable) object, ovModel, factory);
	}

	public static void readProperties(Element node, IOvModelVariationBase object, IOvModel ovModel, IOvModelFactory factory)
			throws OvModelSerialisationException {
		readProperties(node, (IOvModelElement) object, ovModel, factory);

		final String optional = node.getAttribute(OPTIONAL);
		if ((optional != null) && optional.contentEquals(String.valueOf(true))) {
			OvModelUtils.setOptional(object, true);
		} else {
			OvModelUtils.setOptional(object, false);
		}

		final Element metainformation = getChildElementByTagName(node, OV_MODEL_VARIATION_BASE_METAINFORMATION, true);
		read(metainformation, object.getMetainformation(), ovModel, factory);
	}

	public static void readProperties(Element node, IOvModelConstraint object, IOvModel ovModel, IOvModelFactory factory) throws OvModelSerialisationException {
		readProperties(node, (IOvModelElement) object, ovModel, factory);

		final Element metainformation = getChildElementByTagName(node, OV_MODEL_CONSTRAINT_METAINFORMATION, true);
		read(metainformation, object.getMetainformation(), ovModel, factory);

		final List<Element> elements = getChildElements(node, true);
		elements.removeIf(element -> element.getTagName().equals(OV_MODEL_CONSTRAINT_METAINFORMATION));
		if (elements.size() != 2) {
			throw new OvModelWrongCountOfElements(node.getNodeName(), 2, elements.size());
		}

		final IOvModelElement source = read(elements.get(0), ovModel, factory);
		final IOvModelElement target = read(elements.get(1), ovModel, factory);

		if (!(source instanceof IOvModelVariationBase)) {
			throw new OvModelWrongElementException("Source cannot be of type " + source.getClass());
		}
		if (!(target instanceof IOvModelVariationBase)) {
			throw new OvModelWrongElementException("Target cannot be of type " + target.getClass());
		}

		object.setSource((IOvModelVariationBase) source);
		object.setTarget((IOvModelVariationBase) target);
	}

	/**
	 * @param node
	 * @return
	 * @throws OvModelWrongCountOfElements
	 */
	private static Element getChildElementByTagName(Element node, String elementName, boolean throwExceptionWhenNotFound) throws OvModelSerialisationException {
		final List<Element> childElements = getChildElementsByTagName(node, elementName);
		if ((childElements.size() == 0) && !throwExceptionWhenNotFound) {
			return null;
		} else if (childElements.size() != 1) {
			throw new OvModelWrongCountOfElements(elementName, 1, childElements.size());
		}

		return childElements.get(0);
	}

	private static List<Element> getChildElementsByTagName(Element parent, String name) throws OvModelSerialisationException {
		final List<Element> childElements = new ArrayList<Element>();
		for (final Element child : getChildElements(parent, false)) {
			if (name.equals(child.getNodeName())) {
				childElements.add(child);
			}
		}
		return childElements;
	}

	private static List<Element> getChildElements(Element parent, boolean thowExceptionOnEverythingElseThanElementAndText)
			throws OvModelSerialisationException {
		final List<Element> childElements = new ArrayList<Element>();
		for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
			if ((child instanceof Element)) {
				childElements.add((Element) child);
				continue;
			}
			if ((child instanceof Text)) {
				continue;
			}
			if (thowExceptionOnEverythingElseThanElementAndText) {
				throw new OvModelWrongElementException(child.getNodeName());
			}
		}
		return childElements;
	}
}
