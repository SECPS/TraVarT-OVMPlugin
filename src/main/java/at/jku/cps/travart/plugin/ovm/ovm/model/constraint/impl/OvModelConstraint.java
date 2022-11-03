package at.jku.cps.travart.plugin.ovm.ovm.model.constraint.impl;

import at.jku.cps.travart.ovm.model.IIdentifiable;
import at.jku.cps.travart.ovm.model.IOvModelElement;
import at.jku.cps.travart.ovm.model.IOvModelVariationBase;
import at.jku.cps.travart.ovm.model.constraint.IOvModelConstraint;
import at.jku.cps.travart.ovm.model.constraint.IOvModelConstraintMetainformation;
import at.jku.cps.travart.ovm.model.impl.OvModelElement;

/**
 * Represents a concrete implementation of an {@link IOvModelConstraint}.
 *
 * @author johannstoebich
 * @see IOvModelConstraint
 */
public abstract class OvModelConstraint extends OvModelElement implements IOvModelConstraint {

    protected IOvModelConstraintMetainformation metanformation;

    protected IOvModelVariationBase source;

    protected IOvModelVariationBase target;

    public OvModelConstraint() {
        this.metanformation = new OvModelConstraintMetainformation();
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.constraint.IOvModelConstraint#getSource()
     */
    @Override
    public IOvModelVariationBase getSource() {
        return this.source;
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.constraint.IOvModelConstraint#setSource(IOvModelVariationBase)
     */
    @Override
    public void setSource(IOvModelVariationBase source) {
        this.source = source;
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.constraint.IOvModelConstraint#getTarget()
     */
    @Override
    public IOvModelVariationBase getTarget() {
        return this.target;
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.constraint.IOvModelConstraint#setTarget(IOvModelVariationBase)
     */
    @Override
    public void setTarget(IOvModelVariationBase target) {
        this.target = target;
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.configuration.IValidate#isValid()
     */
    @Override
    public boolean isValid() {
        boolean isValid = true;
        isValid = isValid && this.source != null && this.target != null;
        return isValid;
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.constraint.IOvModelConstraint#getMetainformation()
     */
    @Override
    public IOvModelConstraintMetainformation getMetainformation() {
        return this.metanformation;
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.constraint.IOvModelConstraint#getElement(de.ovgu.featureide.core.ovm.model.IIdentifiable)
     */
    @Override
    public IOvModelElement getElement(IIdentifiable identifiable) {
        IOvModelElement element;
        if ((element = super.getElement(identifiable)) != null) {
            return element;
        } else if (this.getSource() != null && (element = this.getSource().getElement(identifiable)) != null) {
            return element;
        } else if (this.getTarget() != null && (element = this.getTarget().getElement(identifiable)) != null) {
            return element;
        }
        return null;
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (this.metanformation == null ? 0 : this.metanformation.hashCode());
        result = prime * result + (this.source == null ? 0 : this.source.hashCode());
        result = prime * result + (this.target == null ? 0 : this.target.hashCode());
        return result;
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        OvModelConstraint other = (OvModelConstraint) obj;
        if (this.metanformation == null) {
            if (other.metanformation != null) {
                return false;
            }
        } else if (!this.metanformation.equals(other.metanformation)) {
            return false;
        }
        if (this.source == null) {
            if (other.source != null) {
                return false;
            }
        } else if (!this.source.equals(other.source)) {
            return false;
        }
        if (this.target == null) {
            if (other.target != null) {
                return false;
            }
        } else if (!this.target.equals(other.target)) {
            return false;
        }
        return true;
    }
}
