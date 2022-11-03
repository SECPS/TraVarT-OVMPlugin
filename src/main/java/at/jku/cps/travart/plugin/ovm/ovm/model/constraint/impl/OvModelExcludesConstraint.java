package at.jku.cps.travart.plugin.ovm.ovm.model.constraint.impl;

import at.jku.cps.travart.plugin.ovm.ovm.model.constraint.IOvModelExcludesConstraint;

/**
 * Represents a concrete implementation of an
 * {@link IOvModelExcludesConstraint}.
 *
 * @author johannstoebich
 * @see IOvModelExcludesConstraint
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
        isValid = isValid && (!this.source.isSelected() || !this.target.isSelected());
        return isValid;
    }
}
