package de.ovgu.featureide.core.ovm.base.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.ovgu.featureide.core.ovm.model.IIdentifyable;
import de.ovgu.featureide.core.ovm.model.IOvModel;
import de.ovgu.featureide.core.ovm.model.IOvModelElement;
import de.ovgu.featureide.core.ovm.model.IOvModelVariationBase;
import de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint;
import de.ovgu.featureide.core.ovm.model.constraint.IOvModelConstraint;
import de.ovgu.featureide.fm.core.base.IPropertyContainer;
import de.ovgu.featureide.fm.core.base.IPropertyContainer.Entry;

/**
 * TODO description
 *
 * @author johannstoebich
 */
public class OvModelUtils {

	/**
	 * @param ovModel
	 * @return
	 */
	public static List<IOvModelVariationPoint> getVariationPoints(IOvModel ovModel) {
		return ovModel.getVariationPoints();
	}

	/**
	 * @param ovModel
	 * @param modelVariantPoint
	 */
	public static void addVariationPoint(IOvModel ovModel, IOvModelVariationPoint ovModelVariationPoint) {
		ovModel.addVariationPoint(ovModelVariationPoint);
	}

	/**
	 * @param ovModel
	 * @return
	 */
	public static List<IOvModelConstraint> getConstraints(IOvModel ovModel) {
		return ovModel.getConstraints();
	}

	/**
	 * @param ovModel
	 * @param ovModelConstraint
	 */
	public static void addConstraint(IOvModel ovModel, IOvModelConstraint ovModelConstraint) {
		ovModel.addConstraint(ovModelConstraint);
	}

	/**
	 * @param ovModelElement
	 * @param properties
	 */
	public static IPropertyContainer getCustomProperties(IOvModel ovModel) {
		return ovModel.getMetainformation().getCustomProperties();
	}

	/**
	 * @param ovModelElement
	 * @param properties
	 */
	public static Set<Entry> getCustomPropertiesEntries(IOvModel ovModel) {
		return ovModel.getMetainformation().getCustomProperties().getProperties();
	}

	/**
	 * @param ovModelElement
	 * @param properties
	 */
	public static void setCustomPropertiesEntries(IOvModel ovModel, Set<Entry> properties) {
		ovModel.getMetainformation().getCustomProperties().setProperties(properties);
	}

	/**
	 * @param identifyable
	 * @return
	 */
	public static String getName(IIdentifyable identifyable) {
		return identifyable.getName();
	}

	/**
	 * @param identifyable
	 * @param name
	 */
	public static void setName(IIdentifyable identifyable, String name) {
		if ((name != null) && !"".contentEquals(name)) {
			identifyable.setName(name);
		}
	}

	/**
	 * @param ovModelConstraint
	 * @return
	 */
	public static String getDescription(IOvModelConstraint ovModelConstraint) {
		return ovModelConstraint.getMetainformation().getDescription();
	}

	/**
	 * @param ovModelConstraint
	 * @param description
	 */
	public static void setDescription(IOvModelConstraint ovModelConstraint, String description) {
		ovModelConstraint.getMetainformation().setDescription(description);
	}

	/**
	 * @param ovModelElement
	 * @return
	 */
	public static IPropertyContainer getCustomProperties(IOvModelVariationBase ovModelVariationBase) {
		return ovModelVariationBase.getMetainformation().getCustomProperties();
	}

	/**
	 * @param ovModelElement
	 * @return
	 */
	public static Set<Entry> getCustomPropertiesEntries(IOvModelVariationBase ovModelVariationBase) {
		return ovModelVariationBase.getMetainformation().getCustomProperties().getProperties();
	}

	/**
	 * @param ovModelElement
	 * @param properties
	 */
	public static void setCustomPropertiesEntries(IOvModelVariationBase ovModelVariationBase, Set<Entry> properties) {
		ovModelVariationBase.getMetainformation().getCustomProperties().setProperties(properties);
	}

	/**
	 * @param ovModelElement
	 * @return
	 */
	public static IPropertyContainer getCustomProperties(IOvModelConstraint ovModelConstraint) {
		return ovModelConstraint.getMetainformation().getCustomProperties();
	}

	/**
	 * @param ovModelElement
	 * @return
	 */
	public static Set<Entry> getCustomPropertiesEntries(IOvModelConstraint ovModelVariationBase) {
		return ovModelVariationBase.getMetainformation().getCustomProperties().getProperties();
	}

	/**
	 * @param ovModelElement
	 * @param properties
	 */
	public static void setCustomPropertiesEntries(IOvModelConstraint ovModelVariationBase, Set<Entry> properties) {
		ovModelVariationBase.getMetainformation().getCustomProperties().setProperties(properties);
	}

	/**
	 * @param ovModelVariationBase
	 * @return
	 */
	public static String getDescription(IOvModelVariationBase ovModelVariationBase) {
		return ovModelVariationBase.getMetainformation().getDescription();
	}

	/**
	 * @param ovModelVariationBase
	 * @param description
	 */
	public static void setDescription(IOvModelVariationBase ovModelVariationBase, String description) {
		ovModelVariationBase.getMetainformation().setDescription(description);
	}

	/**
	 * @param ovModelVariationBase
	 * @return
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
