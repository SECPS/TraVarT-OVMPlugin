package de.ovgu.featureide.core.ovm.model.constraint.impl;

import de.ovgu.featureide.core.ovm.model.constraint.IOvModelRequiresConstraint;

/**
 * Represents a concrete implementation of an {@link IOvModelRequiresConstraint}.
 *
 * @see IOvModelRequiresConstraint
 *
 * @author johannstoebich
 */
public class OvModelRequiresConstraint extends OvModelConstraint implements IOvModelRequiresConstraint {

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.configuration.IValidate#isValid()
	 */
	@Override
	public boolean isValid() {
		boolean isValid = super.isValid();
		isValid = isValid && (!source.isSelected() || target.isSelected());
		return isValid;
	}
}
