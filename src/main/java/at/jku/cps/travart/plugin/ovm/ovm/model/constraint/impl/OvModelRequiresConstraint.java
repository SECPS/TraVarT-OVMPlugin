package at.jku.cps.travart.plugin.ovm.ovm.model.constraint.impl;

import at.jku.cps.travart.ovm.model.constraint.IOvModelRequiresConstraint;

/**
 * Represents a concrete implementation of an
 * {@link IOvModelRequiresConstraint}.
 *
 * @author johannstoebich
 * @see IOvModelRequiresConstraint
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
        boolean s = source.isSelected();
        boolean t = target.isSelected();
        isValid = isValid && (!s || t);
        return isValid;
    }
}