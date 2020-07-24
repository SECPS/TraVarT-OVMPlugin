package de.ovgu.featureide.core.ovm.model;

import java.util.List;

import de.ovgu.featureide.core.ovm.model.constraint.IOvModelConstraint;
import de.ovgu.featureide.fm.core.base.IPropertyContainer;
import de.ovgu.featureide.fm.core.base.impl.Feature;

/**
 * Manages all additional properties of an {@link IOvModelVariationBase} and therefore for an {@link IOvModelVariationPoint} and {@link IOvModelVariant}. The
 * metainformation stores all information which is defined for a feature in FeatureIDE however cannot be represented by an {@link IOvModelVariationBase}.
 *
 */
public interface IOvModelVariationBaseMetainformation {

	/**
	 * This method returns the custom properties of a variation base (variation point or variant). The custom properties represent additional properties of a
	 * variation base overtaken from a feature {@link Feature}. They have been added to a variation base as well so that no information is lost during
	 * transformation.
	 *
	 * @return The returned properties.
	 */
	IPropertyContainer getCustomProperties();

	/**
	 * This method returns the description of a variation base (variation point or variant).
	 *
	 * @return The description of the constraint.
	 */
	String getDescription();

	/**
	 * This method sets the description of a variation base (variation point or variant).
	 *
	 * @param description the description which will be set.
	 */
	void setDescription(String description);

	/**
	 * This method returns the property abstract.
	 *
	 * @return the property abstract.
	 */
	boolean isAbstract();

	/**
	 * This method sets the property abstract of an OvModelVariationBase.
	 *
	 * @param isAbstract the value which will be set.
	 */
	void setAbstract(boolean isAbstract);

	/**
	 * This method returns the property hidden.
	 *
	 * @return the property hidden.
	 */
	boolean isHidden();

	/**
	 * This method sets the property hidden of an OvModelVariationBase.
	 *
	 * @param hidden the value which will be set.
	 */
	void setHidden(boolean hidden);

	/**
	 * This method returns the property partOfModelRoot. This property determines if the variation point came from a feature model constraint or from an
	 * feature.
	 *
	 * @return the property isPartOfModel root.
	 */
	boolean isPartOfOvModelRoot();

	/**
	 * This method sets the property partOfModel of an OvModelVariationBase. This property determines if the variation point came from a feature model
	 * constraint or from an feature.
	 *
	 * @param partOfOvModelRoot sets the parameter to true.
	 */
	void setPartOfOvModelRoot(boolean partOfOvModelRoot);

	/**
	 * This method returns the referenced constraints. If the child of a variation point which came from a constraint is a OvModel constraint, it is stored here
	 * which constraint it is exactly. This is used for transforming the model back to a feature model.
	 *
	 * @return the constraints which are returned.
	 */
	List<IOvModelConstraint> getReferencedConstraints();

	/**
	 * The referenced constraint which is added to to an OvModel variation base. If the child of a variation point which came from a constraint is a OvModel
	 * constraint, it is stored here which constraint it is exactly. This is used for transforming the model back to a feature model.
	 *
	 * @param referencedConstraints the constraint which will be added.
	 */
	void setReferencedConstraints(List<IOvModelConstraint> referencedConstraints);

	@Override
	public int hashCode();

	@Override
	boolean equals(Object obj);
}
