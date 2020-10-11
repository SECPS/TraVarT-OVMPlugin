package de.ovgu.featureide.core.ovm.model.constraint.impl;

import de.ovgu.featureide.core.ovm.model.constraint.IOvModelExcludesConstraint;

/**
 * Represents a concrete implementation of an {@link IOvModelExcludesConstraint}.
 *
 * @see IOvModelExcludesConstraint
 *
 * @author johannstoebich
 */
public class OvModelExcludesConstraint extends OvModelConstraint implements IOvModelExcludesConstraint {

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.configuration.IValidate#isValid()
	 */
	@Override
	public boolean isValid() {
		boolean isValid = super.isValid();
		isValid = isValid && (!source.isSelected() || !target.isSelected());
		return isValid;
	}

}
