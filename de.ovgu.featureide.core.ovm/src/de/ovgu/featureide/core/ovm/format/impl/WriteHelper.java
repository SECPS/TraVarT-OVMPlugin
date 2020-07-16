package de.ovgu.featureide.core.ovm.format.impl;

import static de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistanceProperties.ABSTRACT;
import static de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistanceProperties.ALTERNATIVE;
import static de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistanceProperties.DESCRIPTION;
import static de.ovgu.featureide.core.ovm.format.impl.OvModelXmlPersistanceProperties.FACTORY_ID;
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
import java.util.Collection;
import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import de.ovgu.featureide.core.ovm.base.impl.OvModelUtils;
import de.ovgu.featureide.core.ovm.format.impl.exception.OvModelSerialisationException;
import de.ovgu.featureide.core.ovm.format.impl.exception.OvModelSerialisationNotSupported;
import de.ovgu.featureide.core.ovm.model.IIdentifiable;
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
 * This classes provides the methods for writing an OvModel to a XML document.
 *
 * @author johannstoebich
 */
public class WriteHelper {

	public static void writeModel(IOvModel ovModel, Document doc) throws OvModelSerialisationException {
		final List<IOvModelElement> alreadySerialisedElements = new ArrayList<IOvModelElement>();

		final Element ovModelElement = doc.createElement(OV_MODEL);
		doc.appendChild(ovModelElement);

		writeProperties(ovModel, ovModelElement, alreadySerialisedElements);

		ovModelElement.setAttribute(FACTORY_ID, ovModel.getFactoryId());

		write(ovModel.getMetainformation(), ovModelElement, alreadySerialisedElements);

		// write variant points of constraints after the constraints itself which will leads to nicer serialisation.
		final List<IOvModelVariationPoint> variantPointsNotPartOfRoot = new ArrayList<IOvModelVariationPoint>();
		for (final IOvModelVariationPoint variationPoint : OvModelUtils.getVariationPoints(ovModel)) {
			if (OvModelUtils.isPartOfOvModelRoot(variationPoint)) {
				write(variationPoint, ovModelElement, alreadySerialisedElements);
			} else {
				variantPointsNotPartOfRoot.add(variationPoint);
			}
		}

		for (final IOvModelConstraint constraints : OvModelUtils.getConstraints(ovModel)) {
			if (constraints instanceof IOvModelExcludesConstraint) {
				write((IOvModelExcludesConstraint) constraints, ovModelElement, alreadySerialisedElements);
			} else {
				write((IOvModelRequiresConstraint) constraints, ovModelElement, alreadySerialisedElements);
			}
		}

		// write variant points of constraints after the constraints itself which will leads to nicer serialisation.
		for (final IOvModelVariationPoint variationPoint : variantPointsNotPartOfRoot) {
			write(variationPoint, ovModelElement, alreadySerialisedElements);
		}
	}

	private static void write(IOvModelElement target, Node node, Collection<IOvModelElement> alreadySerialisedElements) throws OvModelSerialisationException {

		if (target instanceof IOvModelVariant) {
			write((IOvModelVariant) target, node, alreadySerialisedElements);
		} else if (target instanceof IOvModelVariationPoint) {
			write((IOvModelVariationPoint) target, node, alreadySerialisedElements);
		} else if (target instanceof IOvModelExcludesConstraint) {
			write((IOvModelExcludesConstraint) target, node, alreadySerialisedElements);
		} else if (target instanceof IOvModelRequiresConstraint) {
			write((IOvModelRequiresConstraint) target, node, alreadySerialisedElements);
		} else {
			throw new OvModelSerialisationNotSupported(target.getClass());
		}
	}

	public static void write(IOvModelVariant object, Node node, Collection<IOvModelElement> alreadySerialisedElements) throws OvModelSerialisationException {
		if (alreadySerialisedElements.contains(object)) {
			final Element ovModelVariantReference = node.getOwnerDocument().createElement(OV_MODEL_VARIANT_REFERENCE);
			node.appendChild(ovModelVariantReference);

			writeProperties((IIdentifiable) object, ovModelVariantReference, alreadySerialisedElements);
		} else {
			final Element ovModelVariant = node.getOwnerDocument().createElement(OV_MODEL_VARIANT);
			node.appendChild(ovModelVariant);

			writeProperties((IOvModelVariationBase) object, ovModelVariant, alreadySerialisedElements);

			alreadySerialisedElements.add(object);
		}

	}

	public static void write(IOvModelVariationPoint object, Node node, Collection<IOvModelElement> alreadySerialisedElements)
			throws OvModelSerialisationException {
		if (alreadySerialisedElements.contains(object)) {
			final Element ovModelVariantReference = node.getOwnerDocument().createElement(OV_MODEL_VARIATION_POINT_REFERENCE);
			node.appendChild(ovModelVariantReference);

			writeProperties((IIdentifiable) object, ovModelVariantReference, alreadySerialisedElements);
		} else {

			final Element ovModelVariationPoint = node.getOwnerDocument().createElement(OV_MODEL_VARIATION_POINT);
			node.appendChild(ovModelVariationPoint);

			writeProperties((IOvModelVariationBase) object, ovModelVariationPoint, alreadySerialisedElements);

			final Attr alternative = node.getOwnerDocument().createAttribute(ALTERNATIVE);
			alternative.setValue(String.valueOf(OvModelUtils.isAlternative(object)));
			ovModelVariationPoint.setAttributeNode(alternative);

			final Attr minChoices = node.getOwnerDocument().createAttribute(MIN_CHOICES);
			minChoices.setValue(String.valueOf(OvModelUtils.getMinChoices(object)));
			ovModelVariationPoint.setAttributeNode(minChoices);

			final Attr maxChoices = node.getOwnerDocument().createAttribute(MAX_CHOICES);
			maxChoices.setValue(String.valueOf(OvModelUtils.getMaxChoices(object)));
			ovModelVariationPoint.setAttributeNode(maxChoices);

			if (OvModelUtils.getMandatoryChildren(object).size() > 0) {
				final Element ovMandatoryChildren = node.getOwnerDocument().createElement(MANDATORY_CHILDREN);
				ovModelVariationPoint.appendChild(ovMandatoryChildren);
				for (final IOvModelVariationBase mandatoryChildren : OvModelUtils.getMandatoryChildren(object)) {
					write(mandatoryChildren, ovMandatoryChildren, alreadySerialisedElements);
				}
			}

			if (OvModelUtils.getOptionalChildren(object).size() > 0) {
				final Element ovOptionalChildren = node.getOwnerDocument().createElement(OPTIONAL_CHILDREN);
				ovModelVariationPoint.appendChild(ovOptionalChildren);
				for (final IOvModelVariationBase optionalChildren : OvModelUtils.getOptionalChildren(object)) {
					write(optionalChildren, ovOptionalChildren, alreadySerialisedElements);
				}
			}

			alreadySerialisedElements.add(object);
		}
	}

	public static void write(IOvModelMetainformation object, Node node, Collection<IOvModelElement> alreadySerialisedElements) {
		final Element ovModelMetainformation = node.getOwnerDocument().createElement(OV_MODEL_METAINFORMATION);
		node.appendChild(ovModelMetainformation);

		final Attr description = node.getOwnerDocument().createAttribute(DESCRIPTION);
		description.setValue(object.getDescription());
		ovModelMetainformation.setAttributeNode(description);

		PropertyHelper.writeProperties(node.getOwnerDocument(), object.getCustomProperties(), ovModelMetainformation);
	}

	public static void write(IOvModelVariationBaseMetainformation object, Node node, Collection<IOvModelElement> alreadySerialisedElements)
			throws OvModelSerialisationException {
		final Element ovModelMetainformation = node.getOwnerDocument().createElement(OV_MODEL_VARIATION_BASE_METAINFORMATION);
		node.appendChild(ovModelMetainformation);

		final Attr attAbstract = node.getOwnerDocument().createAttribute(ABSTRACT);
		attAbstract.setValue(String.valueOf(object.isAbstract()));
		ovModelMetainformation.setAttributeNode(attAbstract);

		final Attr hidden = node.getOwnerDocument().createAttribute(HIDDEN);
		hidden.setValue(String.valueOf(object.isHidden()));
		ovModelMetainformation.setAttributeNode(hidden);

		final Attr partOfOvModelRoot = node.getOwnerDocument().createAttribute(PART_OF_OVMODEL_ROOT);
		partOfOvModelRoot.setValue(String.valueOf(object.isPartOfOvModelRoot()));
		ovModelMetainformation.setAttributeNode(partOfOvModelRoot);

		final Attr description = node.getOwnerDocument().createAttribute(DESCRIPTION);
		description.setValue(object.getDescription());
		ovModelMetainformation.setAttributeNode(description);

		if ((object.getReferencedConstraints() != null) && (object.getReferencedConstraints().size() > 0)) {
			final Element referencedConstraints = node.getOwnerDocument().createElement(REFERENCED_CONSTRAINTS);
			ovModelMetainformation.appendChild(referencedConstraints);
			for (final IOvModelConstraint referencedConstraint : object.getReferencedConstraints()) {
				write(referencedConstraint, referencedConstraints, alreadySerialisedElements);
			}
		}

		PropertyHelper.writeProperties(node.getOwnerDocument(), object.getCustomProperties(), ovModelMetainformation);
	}

	public static void write(IOvModelConstraintMetainformation object, Node node, Collection<IOvModelElement> alreadySerialisedElements) {
		final Element ovModelMetainformation = node.getOwnerDocument().createElement(OV_MODEL_CONSTRAINT_METAINFORMATION);
		node.appendChild(ovModelMetainformation);

		final Attr description = node.getOwnerDocument().createAttribute(DESCRIPTION);
		description.setValue(object.getDescription());
		ovModelMetainformation.setAttributeNode(description);

		PropertyHelper.writeProperties(node.getOwnerDocument(), object.getCustomProperties(), ovModelMetainformation);
	}

	public static void write(IOvModelExcludesConstraint object, Node node, Collection<IOvModelElement> alreadySerialisedElements)
			throws OvModelSerialisationException {
		if (alreadySerialisedElements.contains(object)) {
			final Element ovModelVariantReference = node.getOwnerDocument().createElement(OV_MODEL_EXCLUDES_CONSTRAINT_REFERENCE);
			node.appendChild(ovModelVariantReference);

			writeProperties((IIdentifiable) object, ovModelVariantReference, alreadySerialisedElements);
		} else {
			final Element ovModelExcludesConstraint = node.getOwnerDocument().createElement(OV_MODEL_EXCLUDES_CONSTRAINT);
			node.appendChild(ovModelExcludesConstraint);

			writeProperties(object, ovModelExcludesConstraint, alreadySerialisedElements);

			alreadySerialisedElements.add(object);
		}
	}

	public static void write(IOvModelRequiresConstraint object, Node node, Collection<IOvModelElement> alreadySerialisedElements)
			throws OvModelSerialisationException {

		if (alreadySerialisedElements.contains(object)) {
			final Element ovModelVariantReference = node.getOwnerDocument().createElement(OV_MODEL_REQUIRES_CONSTRAINT_REFERENCE);
			node.appendChild(ovModelVariantReference);

			writeProperties((IIdentifiable) object, ovModelVariantReference, alreadySerialisedElements);
		} else {
			final Element ovModelRequiresConstraint = node.getOwnerDocument().createElement(OV_MODEL_REQUIRES_CONSTRAINT);
			node.appendChild(ovModelRequiresConstraint);

			writeProperties(object, ovModelRequiresConstraint, alreadySerialisedElements);

			alreadySerialisedElements.add(object);
		}
	}

	public static void writeProperties(IIdentifiable object, Element node, Collection<IOvModelElement> alreadySerialisedElements) {
		final Attr name = node.getOwnerDocument().createAttribute(NAME);
		name.setValue(OvModelUtils.getName(object));
		node.setAttributeNode(name);
	}

	public static void writeProperties(IOvModel object, Element node, Collection<IOvModelElement> alreadySerialisedElements) {
		writeProperties((IIdentifiable) object, node, alreadySerialisedElements);
	}

	public static void writeProperties(IOvModelElement object, Element node, Collection<IOvModelElement> alreadySerialisedElements) {
		writeProperties((IIdentifiable) object, node, alreadySerialisedElements);
	}

	public static void writeProperties(IOvModelVariationBase object, Element node, Collection<IOvModelElement> alreadySerialisedElements)
			throws OvModelSerialisationException {
		writeProperties((IOvModelElement) object, node, alreadySerialisedElements);

		final Attr optional = node.getOwnerDocument().createAttribute(OPTIONAL);
		optional.setValue(String.valueOf(OvModelUtils.isOptional(object)));
		node.setAttributeNode(optional);

		write(object.getMetainformation(), node, alreadySerialisedElements);
	}

	public static void writeProperties(IOvModelConstraint object, Element node, Collection<IOvModelElement> alreadySerialisedElements)
			throws OvModelSerialisationException {
		writeProperties((IOvModelElement) object, node, alreadySerialisedElements);

		write(object.getMetainformation(), node, alreadySerialisedElements);

		write(OvModelUtils.getSource(object), node, alreadySerialisedElements);
		write(OvModelUtils.getTarget(object), node, alreadySerialisedElements);
	}
}
