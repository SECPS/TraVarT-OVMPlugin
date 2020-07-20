package de.ovgu.featureide.core.ovm.base.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.ovgu.featureide.core.ovm.model.IIdentifiable;
import de.ovgu.featureide.core.ovm.model.IOvModel;
import de.ovgu.featureide.core.ovm.model.IOvModelVariationBase;
import de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint;
import de.ovgu.featureide.core.ovm.model.constraint.IOvModelConstraint;
import de.ovgu.featureide.fm.core.base.IPropertyContainer;
import de.ovgu.featureide.fm.core.base.IPropertyContainer.Entry;
import de.ovgu.featureide.fm.core.base.impl.Constraint;
import de.ovgu.featureide.fm.core.base.impl.Feature;
import de.ovgu.featureide.fm.core.base.impl.FeatureModel;

/**
 * This class provides utility functions to modify a OvModel.
 *
 * @author johannstoebich
 */
public class OvModelUtils {

	/**
	 * This method gets all variation points of an OvModel.
	 *
	 * @param ovModel the OVModel for which the variation points will returned.
	 * @return the list of variation points.
	 */
	public static List<IOvModelVariationPoint> getVariationPoints(IOvModel ovModel) {
		return ovModel.getVariationPoints();
	}

	/**
	 * This method adds a variation point to an OvModel.
	 *
	 * @param ovModel the OVModel to which the variation point will be added.
	 * @param ovModelVariationPoint the variation point which is added.
	 */
	public static void addVariationPoint(IOvModel ovModel, IOvModelVariationPoint ovModelVariationPoint) {
		ovModel.addVariationPoint(ovModelVariationPoint);
	}

	/**
	 * This method gets all constraints of an OvModel.
	 *
	 * @param ovModel the OvModel for which the constraints will be returned.
	 * @return the list of of constraints.
	 */
	public static List<IOvModelConstraint> getConstraints(IOvModel ovModel) {
		return ovModel.getConstraints();
	}

	/**
	 * This method adds a constraint to an OVModel. A constraint represents a restriction on the model.
	 *
	 * @param ovModel the OVModel to which the constraints will be added.
	 * @param ovModelConstraint the constraint which is added.
	 */
	public static void addConstraint(IOvModel ovModel, IOvModelConstraint ovModelConstraint) {
		ovModel.addConstraint(ovModelConstraint);
	}

	/**
	 *
	 * This method returns the custom properties of the ovModel. The custom properties represent additional properties of an OvModel overtake from the feature
	 * model {@link FeatureModel}. They have been added so that no information is lost during transformation.
	 *
	 * @param ovModel the OVModel for which the properties will be returned.
	 * @return the custom properties of the ovModel.
	 */
	public static IPropertyContainer getCustomProperties(IOvModel ovModel) {
		return ovModel.getMetainformation().getCustomProperties();
	}

	/**
	 *
	 * This method returns the custom properties of the ovModel as a set. The custom properties represent additional properties of an OvModel overtake from the
	 * feature model {@link FeatureModel}. They have been added so that no information is lost during transformation.
	 *
	 * @param ovModel the OVModel for which the properties will be returned.
	 * @return the custom properties of the ovModel as a set.
	 */
	public static Set<Entry> getCustomPropertiesEntries(IOvModel ovModel) {
		return ovModel.getMetainformation().getCustomProperties().getProperties();
	}

	/**
	 * This method sets the properties of the ovModel. The custom properties represent additional properties of an OvModel overtake from the feature model
	 * {@link FeatureModel}. They have been added so that no information is lost during transformation.
	 *
	 *
	 * @param ovModel the OVModel for which the properties will be set.
	 * @param properties the properties which will be set.
	 */
	public static void setCustomPropertiesEntries(IOvModel ovModel, Set<Entry> properties) {
		ovModel.getMetainformation().getCustomProperties().setProperties(properties);
	}

	/**
	 * This method returns the name of an IIdentifiable.
	 *
	 * @param identifiable the identifiable for which the name will be returned.
	 * @return the name of the identifiable.
	 */
	public static String getName(IIdentifiable identifiable) {
		return identifiable.getName();
	}

	/**
	 * This method sets the name of an IIdentifiable.
	 *
	 * @param identifiable the identifiable for which the name will be set.
	 * @param name the name.
	 */
	public static void setName(IIdentifiable identifiable, String name) {
		if ((name != null) && !"".contentEquals(name)) {
			identifiable.setName(name);
		}
	}

	/**
	 * This method returns the description of a constraint.
	 *
	 * @param ovModelConstraint the constraint for which the description will be returned.
	 * @return The description of the constraint
	 */
	public static String getDescription(IOvModelConstraint ovModelConstraint) {
		return ovModelConstraint.getMetainformation().getDescription();
	}

	/**
	 * This method sets the description of a constraint.
	 *
	 * @param ovModelConstraint the constraint for which the description will be set.
	 * @param description the description which will be set.
	 */
	public static void setDescription(IOvModelConstraint ovModelConstraint, String description) {
		ovModelConstraint.getMetainformation().setDescription(description);
	}

	/**
	 * This method returns the custom properties of a variation base (variation point or variant). The custom properties represent additional properties of a
	 * variation point overtaken from a feature {@link Feature}. They have been added to a variation base as well so that no information is lost during
	 * transformation.
	 *
	 * @param ovModelVariationBase the variation base for which the properties will be returned.
	 * @return The returned properties.
	 */
	public static IPropertyContainer getCustomProperties(IOvModelVariationBase ovModelVariationBase) {
		return ovModelVariationBase.getMetainformation().getCustomProperties();
	}

	/**
	 * This method returns the custom properties of a variation base (variation point or variant) as a set. variant). The custom properties represent additional
	 * properties of a variation base overtaken from a feature {@link Feature}. They have been added to a variation base as well so that no information is lost
	 * during transformation.
	 *
	 * @param ovModelVariationBase the variation base for which the properties will be returned.
	 * @return The returned properties.
	 */
	public static Set<Entry> getCustomPropertiesEntries(IOvModelVariationBase ovModelVariationBase) {
		return ovModelVariationBase.getMetainformation().getCustomProperties().getProperties();
	}

	/**
	 * This method sets the properties of a variation base (variation point or variant). The custom properties represent additional properties of a variation
	 * base overtaken from a feature {@link Feature}. They have been added to a variation base as well so that no information is lost during transformation.
	 *
	 * @param ovModelVariationBase the variation base where the properties will be set.
	 * @param properties the properties which will be set.
	 */
	public static void setCustomPropertiesEntries(IOvModelVariationBase ovModelVariationBase, Set<Entry> properties) {
		ovModelVariationBase.getMetainformation().getCustomProperties().setProperties(properties);
	}

	/**
	 * This method returns the custom properties of a constraint. he custom properties represent additional properties of a constraint overtaken from a feature
	 * model constraint {@link Constraint}. They have been added to a constraint as well so that no information is lost during transformation.
	 *
	 *
	 * @param ovModelVariationBase the variation base for which the properties will be returned.
	 * @return The returned properties.
	 */
	public static IPropertyContainer getCustomProperties(IOvModelConstraint ovModelConstraint) {
		return ovModelConstraint.getMetainformation().getCustomProperties();
	}

	/**
	 * This method returns the custom properties of a constraint as a set. he custom properties represent additional properties of a constraint overtaken from a
	 * feature model constraint {@link Constraint}. They have been added to a constraint as well so that no information is lost during transformation.
	 *
	 * @param ovModelVariationBase the variation base for which the properties will be returned as a set.
	 * @return The returned properties.
	 */
	public static Set<Entry> getCustomPropertiesEntries(IOvModelConstraint ovModelConstraint) {
		return ovModelConstraint.getMetainformation().getCustomProperties().getProperties();
	}

	/**
	 * This method sets the properties of an constraint.he custom properties represent additional properties of a constraint overtaken from a feature model
	 * constraint {@link Constraint}. They have been added to a constraint as well so that no information is lost during transformation.
	 *
	 * @param ovModelConstraint the constraint where the properties will be set.
	 * @param properties the properties which will be set.
	 */
	public static void setCustomPropertiesEntries(IOvModelConstraint ovModelConstraint, Set<Entry> properties) {
		ovModelConstraint.getMetainformation().getCustomProperties().setProperties(properties);
	}

	/**
	 * This method returns the description of a variation base (variation point or variant).
	 *
	 * @param ovModelVariationBase the variation base for which the description will be returned.
	 * @return The description of the variaton base.
	 */
	public static String getDescription(IOvModelVariationBase ovModelVariationBase) {
		return ovModelVariationBase.getMetainformation().getDescription();
	}

	/**
	 * This method sets the description of a variation base (variation point or variant).
	 *
	 * @param ovModelVariationBase the variation base for which the description will be set.
	 * @param description the description which will be set.
	 */
	public static void setDescription(IOvModelVariationBase ovModelVariationBase, String description) {
		ovModelVariationBase.getMetainformation().setDescription(description);
	}

	/**
	 * This method returns the referenced constraints. If the child of a variation point which came from a constraint is a OvModel constraint, it is stored here
	 * which constraint it is exactly. This is used for transforming the model back to a feature model.
	 *
	 * @param ovModelVariationBase the variation base for which the constraints will be returned.
	 * @return the constraints which are returned.
	 */
	public static List<IOvModelConstraint> getReferencedConstraints(IOvModelVariationBase ovModelVariationBase) {
		if (ovModelVariationBase.getMetainformation().getReferencedConstraints() == null) {
			ovModelVariationBase.getMetainformation().setReferencedConstraints(new ArrayList<IOvModelConstraint>());
		}
		return ovModelVariationBase.getMetainformation().getReferencedConstraints();
	}

	/**
	 * The referenced constraint which is added to to an OvModel variation base. If the child of a variation point which came from a constraint is a OvModel
	 * constraint, it is stored here which constraint it is exactly. This is used for transforming the model back to a feature model.
	 *
	 * @param ovModelVariationBase a referenced constraint where the constraint will be added.
	 * @param ovModelConstraint the constraint which will be added.
	 */
	public static void addReferencedConstraint(IOvModelVariationBase ovModelVariationBase, IOvModelConstraint ovModelConstraint) {
		if (ovModelVariationBase.getMetainformation().getReferencedConstraints() == null) {
			ovModelVariationBase.getMetainformation().setReferencedConstraints(new ArrayList<IOvModelConstraint>());
		}
		ovModelVariationBase.getMetainformation().getReferencedConstraints().add(ovModelConstraint);
	}

	/**
	 * This method returns the property partOfModelRoot. This property determines if the variation point came from a feature model constraint or from an
	 * feature.
	 *
	 * @param ovModelVariationBase the variation base where it will be returned.
	 * @return the property isPartOfModel root.
	 */
	public static boolean isPartOfOvModelRoot(IOvModelVariationBase ovModelVariationBase) {
		return ovModelVariationBase.getMetainformation().isPartOfOvModelRoot();
	}

	/**
	 * This method sets the property partOfModel of an OvModelVariationBase. This property determines if the variation point came from a feature model
	 * constraint or from an feature.
	 *
	 * @param ovModelVariationBase the variation base where the property will be set.
	 * @param partOfOvModelRoot sets the parameter to true.
	 */
	public static void setPartOfOvModelRoot(IOvModelVariationBase ovModelVariationBase, boolean partOfOvModelRoot) {
		ovModelVariationBase.getMetainformation().setPartOfOvModelRoot(partOfOvModelRoot);
	}

	/**
	 * This method returns the property abstract.
	 *
	 * @param ovModelVariationBase the variation base where the property will returned.
	 * @return the property abstract.
	 */
	public static boolean isAbstract(IOvModelVariationBase ovModelVariationBase) {
		return ovModelVariationBase.getMetainformation().isAbstract();
	}

	/**
	 * This method sets the property abstract of an OvModelVariationBase.
	 *
	 * @param ovModelVariationBase the variation base where the property will be set.
	 * @param isAbstract the value which will be set.
	 */
	public static void setAbstract(IOvModelVariationBase ovModelVariationBase, boolean isAbstract) {
		ovModelVariationBase.getMetainformation().setAbstract(isAbstract);
	}

	/**
	 * This method returns the property optional.
	 *
	 * @param ovModelVariationBase the variation base (variation point or variant) where the property will returned.
	 * @return the property optional.
	 */
	public static boolean isOptional(IOvModelVariationBase ovModelVariationBase) {
		return ovModelVariationBase.isOptional();
	}

	/**
	 * This method sets the property optional of an OvModelVariationBase (variation point or variant).
	 *
	 * @param ovModelVariationBase the variation base (variation point or variant) where the property will be set.
	 * @param optional the value which will be set.
	 */
	public static void setOptional(IOvModelVariationBase ovModelVariationBase, boolean optional) {
		ovModelVariationBase.setOptional(optional);
	}

	/**
	 * This method returns the property hidden.
	 *
	 * @param ovModelVariationBase the variation base where the property will returned.
	 * @return the property hidden.
	 */
	public static boolean isHidden(IOvModelVariationBase ovModelVariationBase) {
		return ovModelVariationBase.getMetainformation().isHidden();
	}

	/**
	 * This method sets the property hidden of an OvModelVariationBase.
	 *
	 * @param ovModelVariationBase the variation base where the property will be set.
	 * @param hidden the value which will be set.
	 */
	public static void setHidden(IOvModelVariationBase ovModelVariationBase, boolean hidden) {
		ovModelVariationBase.getMetainformation().setHidden(hidden);
	}

	/**
	 * This method returns true, if there are mandatory children defined for this variation point. It is defined if <code>lenght > 0</code>.
	 *
	 * @param ovModelVariationPoint the variation point where the property will returned.
	 * @return <code>true</code> if there are mandatory children.
	 */
	public static boolean hasMandatoryChildren(IOvModelVariationPoint ovModelVariationPoint) {
		return ovModelVariationPoint.hasMandatoryChildren();
	}

	/**
	 * This method returns the amount of mandatory children defined for this variation point.
	 *
	 * @param ovModelVariationPoint the variation point where the property will returned.
	 * @return the amount of optional children.
	 */
	public static int getMandatoryChildrenCount(IOvModelVariationPoint ovModelVariationPoint) {
		return ovModelVariationPoint.getMandatoryChildrenCount();
	}

	/**
	 * This method returns all mandatory children defined in a variation point.
	 *
	 * @param ovModelVariationPoint the variation point where the property will returned.
	 * @return the amount of mandatory children.
	 */
	public static List<IOvModelVariationBase> getMandatoryChildren(IOvModelVariationPoint ovModelVariationPoint) {
		if (ovModelVariationPoint.getMandatoryChildren() == null) {
			ovModelVariationPoint.setMandatoryChildren(new ArrayList<IOvModelVariationBase>());
		}
		return ovModelVariationPoint.getMandatoryChildren();
	}

	/**
	 * This method overwrites all mandatory children of a variation point. This method clones the children list.
	 *
	 * @param ovModelVariationPoint the variation point for which the optional children will be set.
	 * @param ovChildren the new list of mandatory children.
	 */
	public static void setMandatoryChildren(IOvModelVariationPoint ovModelVariationPoint, List<IOvModelVariationBase> ovChildren) {
		ovModelVariationPoint.setMandatoryChildren(new ArrayList<IOvModelVariationBase>(ovChildren));
	}

	/**
	 * This method adds an child to the list of mandatory children.
	 *
	 * @param ovModelVariationPoint the variation point to which the child will be added.
	 * @param ovChild the child which should be added.
	 */
	public static void addMandatoryChild(IOvModelVariationPoint ovModelVariationPoint, IOvModelVariationBase ovChild) {
		if (ovModelVariationPoint.getMandatoryChildren() == null) {
			ovModelVariationPoint.setMandatoryChildren(new ArrayList<IOvModelVariationBase>());
		}
		ovModelVariationPoint.addMandatoryChild(ovChild);
	}

	/**
	 * This method returns true, if there are optional children defined for this variation point. It is defined if <code>lenght > 0</code>.
	 *
	 * @param ovModelVariationPoint the variation point where the property will returned.
	 * @return <code>true</code> if there are optional children.
	 */
	public static boolean hasOptionalChildren(IOvModelVariationPoint ovModelVariantPoint) {
		return ovModelVariantPoint.hasOptionalChildren();
	}

	/**
	 * This method returns the amount of optional children defined for this variation point.
	 *
	 * @param ovModelVariationPoint the variation point where the property will returned.
	 * @return the amount of optional children.
	 */
	public static int getOptionalChildrenCount(IOvModelVariationPoint ovModelVariantPoint) {
		return ovModelVariantPoint.getOptionalChildrenCount();
	}

	/**
	 * This method returns all optional children defined in a variation point.
	 *
	 * @param ovModelVariationPoint the variation point where the property will returned.
	 * @return the amount of mandatory children.
	 */
	public static List<IOvModelVariationBase> getOptionalChildren(IOvModelVariationPoint ovModelVariationPoint) {
		if (ovModelVariationPoint.getOptionalChildren() == null) {
			ovModelVariationPoint.setOptionalChildren(new ArrayList<IOvModelVariationBase>());
		}
		return ovModelVariationPoint.getOptionalChildren();
	}

	/**
	 * This method overwrites all optional children of a variation point. This method clones the list.
	 *
	 * @param ovModelVariationPoint the variation point for which the optional children will be set.
	 * @param ovChildren the new list of optional children.
	 */
	public static void setOptionalChildren(IOvModelVariationPoint ovModelVariationPoint, List<IOvModelVariationBase> ovChildren) {
		ovModelVariationPoint.setOptionalChildren(new ArrayList<IOvModelVariationBase>(ovChildren));
	}

	/**
	 * This method adds an child to the list of optional children.
	 *
	 * @param ovModelVariationPoint the variation point to which the child will be added.
	 * @param ovChild the child which should be added.
	 */
	public static void addOptionalChild(IOvModelVariationPoint ovModelVariationPoint, IOvModelVariationBase ovChild) {
		if (ovModelVariationPoint.getOptionalChildren() == null) {
			ovModelVariationPoint.setOptionalChildren(new ArrayList<IOvModelVariationBase>());
		}
		ovModelVariationPoint.addOptionalChild(ovChild);
	}

	/**
	 * This method returns the property alternative.
	 *
	 * @param ovModelVariationBase the variation point where the property will returned.
	 * @return the property alternative.
	 */
	public static boolean isAlternative(IOvModelVariationPoint ovModelVariationPoint) {
		return ovModelVariationPoint.isAlternative();
	}

	/**
	 * This method sets the property alternative of an OvModelVariationPoint.
	 *
	 * @param ovModelVariationPoint the variation point where the property will be set.
	 * @param alternative the value which will be set.
	 */
	public static void setAlternative(IOvModelVariationPoint ovModelVariationPoint, boolean alternative) {
		ovModelVariationPoint.setAlternative(alternative);
	}

	/**
	 * This method returns true, if there are is a minimum amount of choices defined for this variation point. It is defined if <code>minChoices != -1 </code>.
	 *
	 * @param ovModelVariationPoint the variation point where the property will returned.
	 * @return <code>true</code> if the property min choices is defined.
	 */
	public static boolean hasMinChoices(IOvModelVariationPoint ovModelVariationPoint) {
		return ovModelVariationPoint.hasMinChoices();
	}

	/**
	 * This method returns the minimum amount of children which have to be chosen at this variation point.
	 *
	 * @param ovModelVariationPoint the variation point where the property will returned.
	 * @return the minimum amount of choices.
	 */
	public static int getMinChoices(IOvModelVariationPoint ovModelVariationPoint) {
		return ovModelVariationPoint.getMinChoices();
	}

	/**
	 * This method sets the properties min choices of an OvModelVariationPoint. It determines the minimum children which has to be chosen.
	 *
	 * @param ovModelVariationPoint the variation point where the property will set.
	 * @param min the value which will be set.
	 */
	public static void setMinChoices(IOvModelVariationPoint ovModelVariationPoint, int min) {
		ovModelVariationPoint.setMinChoices(min);
	}

	/**
	 * This method returns true, if there is a maximal amount of choices defined for this variation point. It is defined if <code>maxChoices != -1 </code>.
	 *
	 * @param ovModelVariationPoint the variation point where the property will returned.
	 * @return <code>true</code> if the property max choices is defined.
	 */
	public static boolean hasMaxChoices(IOvModelVariationPoint ovModelVariationPoint) {
		return ovModelVariationPoint.hasMaxChoices();
	}

	/**
	 * This method returns the maximum amount of children which can be chosen at this variation point.
	 *
	 * @param ovModelVariationPoint the variation point where the property will returned.
	 * @return the maximum amount of choices.
	 */
	public static int getMaxChoices(IOvModelVariationPoint ovModelVariationPoint) {
		return ovModelVariationPoint.getMaxChoices();
	}

	/**
	 * This method sets the property max choices of an OvModelVariationPoint. It determines the maximum children which can be chosen.
	 *
	 * @param ovModelVariationPoint the variation point where the property will set.
	 * @param min the value which will be set.
	 */
	public static void setMaxChoices(IOvModelVariationPoint ovModelVariationPoint, int max) {
		ovModelVariationPoint.setMaxChoices(max);
	}

	/**
	 * This method returns the well defined source of a constraint as variation base (variation point or variant).
	 *
	 * @param ovModelConstraint the constraint where the property will returned.
	 * @return the source of the variation base (variation point or variant).
	 */
	public static IOvModelVariationBase getSource(IOvModelConstraint ovModelConstraint) {
		return ovModelConstraint.getSource();
	}

	/**
	 * This method sets the source of an OvModel constraint. An OvModel constrain has always a well defined source.
	 *
	 * @param ovModelConstraint the constraint where the property will set.
	 * @param source the source as variation base (variation point or variant) of the constraint.
	 */
	public static void setSource(IOvModelConstraint ovModelConstraint, IOvModelVariationBase source) {
		ovModelConstraint.setSource(source);
	}

	/**
	 * This method returns the well defined target of a constraint as variation base (variation point or variant).
	 *
	 * @param ovModelConstraint the constraint where the property will returned.
	 * @return the target of the constraint as variation base (variation point or variant).
	 */
	public static IOvModelVariationBase getTarget(IOvModelConstraint ovModelConstraint) {
		return ovModelConstraint.getTarget();
	}

	/**
	 * This method sets the target of an OvModel constraint. An OvModel constrain has always a well defined target.
	 *
	 * @param ovModelConstraint the constraint where the property will set.
	 * @param target the target variation base (variation point or variant) of the constraint.
	 */
	public static void setTarget(IOvModelConstraint ovModelConstraint, IOvModelVariationBase target) {
		ovModelConstraint.setTarget(target);
	}

}
