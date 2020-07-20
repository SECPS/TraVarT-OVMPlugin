package de.ovgu.featureide.core.ovm.model.constraint;

import de.ovgu.featureide.fm.core.base.IPropertyContainer;
import de.ovgu.featureide.fm.core.base.impl.Constraint;

/**
 * Manages all additional properties of a constraint. or variant. A constraint stores all information which is defined for a constraint in FeatureIDE however
 * cannot be represented by an OVM. In this case it is solely the description and the properties.
 *
 */
public interface IOvModelConstraintMetainformation {

	/**
	 * This method returns the custom properties of a constraint. The custom properties represent additional properties of a constraint overtaken from a feature
	 * model constraint {@link Constraint}. They have been added to a constraint as well so that no information is lost during transformation.
	 *
	 * @return The returned properties.
	 */
	IPropertyContainer getCustomProperties();

	/**
	 * This method returns the description of a constraint.
	 *
	 * @return The description of the constraint
	 */
	String getDescription();

	/**
	 * This method sets the description of a constraint.
	 *
	 * @param description the description which will be set.
	 */
	void setDescription(String description);

	@Override
	public int hashCode();

	@Override
	boolean equals(Object obj);
}
