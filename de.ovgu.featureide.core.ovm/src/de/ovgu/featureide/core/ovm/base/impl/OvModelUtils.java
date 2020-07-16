package de.ovgu.featureide.core.ovm.base.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.ovgu.featureide.core.ovm.model.IIdentifiable;
import de.ovgu.featureide.core.ovm.model.IOvModel;
import de.ovgu.featureide.core.ovm.model.IOvModelElement;
import de.ovgu.featureide.core.ovm.model.IOvModelVariationBase;
import de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint;
import de.ovgu.featureide.core.ovm.model.constraint.IOvModelConstraint;
import de.ovgu.featureide.fm.core.base.IPropertyContainer;
import de.ovgu.featureide.fm.core.base.IPropertyContainer.Entry;

/**
 * This class provides utility functions to modify a OvModel.
 *
 * @author johannstoebich
 */
public class OvModelUtils {

	/**
	 * This method gets all variation points of an OVModel.
	 *
	 * @param ovModel the OVModel for which the variation points should be returned.
	 * @return the list of variation points.
	 */
	public static List<IOvModelVariationPoint> getVariationPoints(IOvModel ovModel) {
		return ovModel.getVariationPoints();
	}

	/**
	 * This method adds a variation point to an OVModel.
	 *
	 * @param ovModel the OVModel to which the variation point should be added.
	 * @param ovModelVariationPoint the variation point which is added.
	 */
	public static void addVariationPoint(IOvModel ovModel, IOvModelVariationPoint ovModelVariationPoint) {
		ovModel.addVariationPoint(ovModelVariationPoint);
	}

	/**
	 * This method gets all variation points of an OVModel.
	 *
	 * @param ovModel the OVModel for which the constraints should be returned.
	 * @return the list of of cosntraints.
	 */
	public static List<IOvModelConstraint> getConstraints(IOvModel ovModel) {
		return ovModel.getConstraints();
	}

	/**
	 * This method adds a constraint to an OVModel.
	 *
	 * @param ovModel the OVModel to which the constraints should be added.
	 * @param ovModelConstraint the constraint which is added.
	 */
	public static void addConstraint(IOvModel ovModel, IOvModelConstraint ovModelConstraint) {
		ovModel.addConstraint(ovModelConstraint);
	}

	/**
	 *
	 * This method returns the custom properties of the ovModel.
	 *
	 * @param ovModel the OVModel for which the properties should be returned.
	 * @return the custom properties of the ovModel.
	 */
	public static IPropertyContainer getCustomProperties(IOvModel ovModel) {
		return ovModel.getMetainformation().getCustomProperties();
	}

	/**
	 *
	 * This method returns the custom properties of the ovModel as a set.
	 *
	 * @param ovModel the OVModel for which the properties should be returned.
	 * @return the custom properties of the ovModel as a set.
	 */
	public static Set<Entry> getCustomPropertiesEntries(IOvModel ovModel) {
		return ovModel.getMetainformation().getCustomProperties().getProperties();
	}

	/**
	 * This method sets the properties of the ovModel.
	 *
	 * @param ovModel the OVModel for which the properties should be set.
	 * @param properties the properties which should be set.
	 */
	public static void setCustomPropertiesEntries(IOvModel ovModel, Set<Entry> properties) {
		ovModel.getMetainformation().getCustomProperties().setProperties(properties);
	}

	/**
	 * This method returns the name of an IIdentifiable.
	 *
	 * @param identifiable the identifiable for which the name should be returned.
	 * @return the name of the identifiable.
	 */
	public static String getName(IIdentifiable identifiable) {
		return identifiable.getName();
	}

	/**
	 * This method sets the name of an IIdentifiable.
	 *
	 * @param identifiable the identifiable for which the name should be set.
	 * @param name the name.
	 */
	public static void setName(IIdentifiable identifiable, String name) {
		if ((name != null) && !"".contentEquals(name)) {
			identifiable.setName(name);
		}
	}

	/**
	 * This method returns the description of an constraint.
	 *
	 * @param ovModelConstraint the constraint for which the description should be returned.
	 * @return The description of the constraint
	 */
	public static String getDescription(IOvModelConstraint ovModelConstraint) {
		return ovModelConstraint.getMetainformation().getDescription();
	}

	/**
	 * This method sets the description of a constraint.
	 *
	 * @param ovModelConstraint the constraint for which the description should be set.
	 * @param description the description which should be set.
	 */
	public static void setDescription(IOvModelConstraint ovModelConstraint, String description) {
		ovModelConstraint.getMetainformation().setDescription(description);
	}

	/**
	 * This method returns the custom properties of an variation base (variation point or variant).
	 *
	 * @param ovModelVariationBase the variation base for which the properties should be returned.
	 * @return The returned properties.
	 */
	public static IPropertyContainer getCustomProperties(IOvModelVariationBase ovModelVariationBase) {
		return ovModelVariationBase.getMetainformation().getCustomProperties();
	}

	/**
	 * This method returns the custom properties of a variation base (variation point or variant) as a set.
	 *
	 * @param ovModelVariationBase the variation base for which the properties should be returned.
	 * @return The returned properties.
	 */
	public static Set<Entry> getCustomPropertiesEntries(IOvModelVariationBase ovModelVariationBase) {
		return ovModelVariationBase.getMetainformation().getCustomProperties().getProperties();
	}

	/**
	 * This method sets the properties of an variation base.
	 *
	 * @param ovModelVariationBase the variation base where the properties should be set.
	 * @param properties the properties which should be set.
	 */
	public static void setCustomPropertiesEntries(IOvModelVariationBase ovModelVariationBase, Set<Entry> properties) {
		ovModelVariationBase.getMetainformation().getCustomProperties().setProperties(properties);
	}

	/**
	 * This method returns the custom properties of a constraint.
	 *
	 * @param ovModelVariationBase the variation base for which the properties should be returned.
	 * @return The returned properties.
	 */
	public static IPropertyContainer getCustomProperties(IOvModelConstraint ovModelConstraint) {
		return ovModelConstraint.getMetainformation().getCustomProperties();
	}

	/**
	 * This method returns the custom properties of a constraint as a set.
	 *
	 * @param ovModelVariationBase the variation base for which the properties should be returned as a set.
	 * @return The returned properties.
	 */
	public static Set<Entry> getCustomPropertiesEntries(IOvModelConstraint ovModelConstraint) {
		return ovModelConstraint.getMetainformation().getCustomProperties().getProperties();
	}

	/**
	 * This method sets the properties of an constraint.
	 *
	 * @param ovModelConstraint the constraint where the properties should be set.
	 * @param properties the properties which should be set.
	 */
	public static void setCustomPropertiesEntries(IOvModelConstraint ovModelConstraint, Set<Entry> properties) {
		ovModelConstraint.getMetainformation().getCustomProperties().setProperties(properties);
	}

	/**
	 * This method returns the description of a variation base (variation point or variant).
	 *
	 * @param ovModelVariationBase the variation base for which the description should be returned.
	 * @return The description of the constraint.
	 */
	public static String getDescription(IOvModelVariationBase ovModelVariationBase) {
		return ovModelVariationBase.getMetainformation().getDescription();
	}

	/**
	 * This method sets the description of a variation base (variation point or variant).
	 *
	 * @param ovModelVariationBase the variation base for which the description should be set.
	 * @param description the description which should be set.
	 */
	public static void setDescription(IOvModelVariationBase ovModelVariationBase, String description) {
		ovModelVariationBase.getMetainformation().setDescription(description);
	}

	/**
	 * This method returns the referenced constraints.
	 *
	 * @param ovModelVariationBase the variation base for which the constraints should be returend
	 * @return the constraints which are returned.
	 */
	public static List<IOvModelConstraint> getReferencedConstraints(IOvModelVariationBase ovModelVariationBase) {
		if (ovModelVariationBase.getMetainformation().getReferencedConstraints() == null) {
			ovModelVariationBase.getMetainformation().setReferencedConstraints(new ArrayList<IOvModelConstraint>());
		}
		return ovModelVariationBase.getMetainformation().getReferencedConstraints();
	}

	/**
	 * @param ovModelVariationBase
	 * @param ovModelConstraint
	 */
	public static void addReferencedConstraint(IOvModelVariationBase ovModelVariationBase, IOvModelConstraint ovModelConstraint) {
		if (ovModelVariationBase.getMetainformation().getReferencedConstraints() == null) {
			ovModelVariationBase.getMetainformation().setReferencedConstraints(new ArrayList<IOvModelConstraint>());
		}
		ovModelVariationBase.getMetainformation().getReferencedConstraints().add(ovModelConstraint);
	}

	/**
	 * @param ovModelVariationBase
	 * @return
	 */
	public static boolean isPartOfOvModelRoot(IOvModelVariationBase ovModelVariationBase) {
		return ovModelVariationBase.getMetainformation().isPartOfOvModelRoot();
	}

	/**
	 * @param ovModelVariationBase
	 * @param partOfOvModelRoot
	 */
	public static void setPartOfOvModelRoot(IOvModelVariationBase ovModelVariationBase, boolean partOfOvModelRoot) {
		ovModelVariationBase.getMetainformation().setPartOfOvModelRoot(partOfOvModelRoot);
	}

	/**
	 * @param ovModelVariationBase
	 * @return
	 */
	public static boolean isAbstract(IOvModelVariationBase ovModelVariationBase) {
		return ovModelVariationBase.getMetainformation().isAbstract();
	}

	/**
	 * @param ovModelVariationBase
	 * @param value
	 */
	public static void setAbstract(IOvModelVariationBase ovModelVariationBase, boolean value) {
		ovModelVariationBase.getMetainformation().setAbstract(value);
	}

	/**
	 * @param ovModelVariationBase
	 * @return
	 */
	public static boolean isOptional(IOvModelVariationBase ovModelVariationBase) {
		return ovModelVariationBase.isOptional();
	}

	/**
	 * @param ovModelVariationBase
	 * @param mandatory
	 */
	public static void setOptional(IOvModelVariationBase ovModelVariationBase, boolean optional) {
		ovModelVariationBase.setOptional(optional);
	}

	/**
	 * @param ovModelVariationBase
	 * @return
	 */
	public static boolean isHidden(IOvModelVariationBase ovModelVariationBase) {
		return ovModelVariationBase.getMetainformation().isHidden();
	}

	/**
	 * @param ovModelVariationBase
	 * @param hidden
	 */
	public static void setHidden(IOvModelVariationBase ovModelVariationBase, boolean hidden) {
		ovModelVariationBase.getMetainformation().setHidden(hidden);
	}

	/**
	 * @param ovModelVariantPoint
	 * @return
	 */
	public static boolean hasMandatoryChildren(IOvModelVariationPoint ovModelVariationPoint) {
		return ovModelVariationPoint.hasMandatoryChildren();
	}

	/**
	 * @param ovModelVariantPoint
	 * @return
	 */
	public static int getMandatoryChildrenCount(IOvModelVariationPoint ovModelVariationPoint) {
		return ovModelVariationPoint.getMandatoryChildrenCount();
	}

	/**
	 * @param ovModelVariationPoint
	 * @return
	 */
	public static List<IOvModelVariationBase> getMandatoryChildren(IOvModelVariationPoint ovModelVariationPoint) {
		if (ovModelVariationPoint.getMandatoryChildren() == null) {
			ovModelVariationPoint.setMandatoryChildren(new ArrayList<IOvModelVariationBase>());
		}
		return ovModelVariationPoint.getMandatoryChildren();
	}

	/**
	 * @param ovModelVariationPoint
	 * @param ovChildren
	 */
	public static void setMandatoryChildren(IOvModelVariationPoint ovModelVariationPoint, List<IOvModelVariationBase> ovChildren) {
		ovModelVariationPoint.setMandatoryChildren(ovChildren);
	}

	/**
	 * @param ovModelVariationPoint
	 * @param ovChild
	 */
	public static void addMandatoryChild(IOvModelVariationPoint ovModelVariationPoint, IOvModelVariationBase ovChild) {
		if (ovModelVariationPoint.getMandatoryChildren() == null) {
			ovModelVariationPoint.setMandatoryChildren(new ArrayList<IOvModelVariationBase>());
		}
		ovModelVariationPoint.addMandatoryChild(ovChild);
	}

	/**
	 * @param ovModelVariantPoint
	 * @return
	 */
	public static boolean hasOptionalChildren(IOvModelVariationPoint ovModelVariantPoint) {
		return ovModelVariantPoint.hasOptionalChildren();
	}

	/**
	 * @param ovModelVariantPoint
	 * @return
	 */
	public static int getOptionalChildrenCount(IOvModelVariationPoint ovModelVariantPoint) {
		return ovModelVariantPoint.getOptionalChildrenCount();
	}

	/**
	 * @param ovModelVariationPoint
	 * @return
	 */
	public static List<IOvModelVariationBase> getOptionalChildren(IOvModelVariationPoint ovModelVariationPoint) {
		if (ovModelVariationPoint.getOptionalChildren() == null) {
			ovModelVariationPoint.setOptionalChildren(new ArrayList<IOvModelVariationBase>());
		}
		return ovModelVariationPoint.getOptionalChildren();
	}

	/**
	 * @param ovModelVariationPoint
	 * @param ovChildren
	 */
	public static void setOptionalChildren(IOvModelVariationPoint ovModelVariationPoint, List<IOvModelVariationBase> ovChildren) {
		ovModelVariationPoint.setOptionalChildren(ovChildren);
	}

	/**
	 * @param ovModelVariationPoint
	 * @param ovChild
	 */
	public static void addOptionalChild(IOvModelVariationPoint ovModelVariationPoint, IOvModelVariationBase ovChild) {
		if (ovModelVariationPoint.getOptionalChildren() == null) {
			ovModelVariationPoint.setOptionalChildren(new ArrayList<IOvModelVariationBase>());
		}
		ovModelVariationPoint.addOptionalChild(ovChild);
	}

	/**
	 * @param ovModelVariationPoint
	 * @return
	 */
	public static boolean isAlternative(IOvModelVariationPoint ovModelVariationPoint) {
		return ovModelVariationPoint.isAlternative();
	}

	/**
	 * @param ovModelVariationPoint
	 * @param alternative
	 */
	public static void setAlternative(IOvModelVariationPoint ovModelVariationPoint, boolean alternative) {
		ovModelVariationPoint.setAlternative(alternative);
	}

	/**
	 * @param ovModelVariationBase
	 * @return
	 */
	public static boolean hasMinChoices(IOvModelVariationPoint ovModelVariationPoint) {
		return ovModelVariationPoint.hasMinChoices();
	}

	/**
	 * @param ovModelVariationBase
	 * @return
	 */
	public static int getMinChoices(IOvModelVariationPoint ovModelVariationPoint) {
		return ovModelVariationPoint.getMinChoices();
	}

	/**
	 * @param ovModelVariationBase
	 * @param min
	 */
	public static void setMinChoices(IOvModelVariationPoint ovModelVariationPoint, int min) {
		ovModelVariationPoint.setMinChoices(min);
	}

	/**
	 * @param ovModelVariationBase
	 * @return
	 */
	public static boolean hasMaxChoices(IOvModelVariationPoint ovModelVariationPoint) {
		return ovModelVariationPoint.hasMaxChoices();
	}

	/**
	 * @param ovModelVariationBase
	 * @return
	 */
	public static int getMaxChoices(IOvModelVariationPoint ovModelVariationPoint) {
		return ovModelVariationPoint.getMaxChoices();
	}

	/**
	 * @param ovModelVariationBase
	 * @param max
	 */
	public static void setMaxChoices(IOvModelVariationPoint ovModelVariationPoint, int max) {
		ovModelVariationPoint.setMaxChoices(max);
	}

	/**
	 * @param ovModelConstraint
	 * @return
	 */
	public static IOvModelElement getSource(IOvModelConstraint ovModelConstraint) {
		return ovModelConstraint.getSource();
	}

	/**
	 * @param ovModelConstraint
	 * @param source
	 */
	public static void setSource(IOvModelConstraint ovModelConstraint, IOvModelVariationBase source) {
		ovModelConstraint.setSource(source);
	}

	/**
	 * @param ovModelConstraint
	 * @return
	 */
	public static IOvModelElement getTarget(IOvModelConstraint ovModelConstraint) {
		return ovModelConstraint.getTarget();
	}

	/**
	 * @param ovModelConstraint
	 * @param target
	 */
	public static void setTarget(IOvModelConstraint ovModelConstraint, IOvModelVariationBase target) {
		ovModelConstraint.setTarget(target);
	}

}
