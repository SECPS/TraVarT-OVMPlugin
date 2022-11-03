package at.jku.cps.travart.plugin.ovm.ovm.model.impl;

import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationBase;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationBaseMetainformation;

/**
 * Represents a concrete implementation of an {@link IOvModelVariationBase}.
 *
 * @author johannstoebich
 * @see IOvModelVariationBase
 */
public abstract class OvModelVariationBase extends OvModelElement implements IOvModelVariationBase {

    protected IOvModelVariationBaseMetainformation metainformation;
    protected boolean optional;
    private boolean selected;

    public OvModelVariationBase() {
        super();
        this.metainformation = new OvModelVariationBaseMetainformation();
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.IOvModelVariationBase#getMetaInformation()
     */
    @Override
    public IOvModelVariationBaseMetainformation getMetainformation() {
        return this.metainformation;
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.IOvModelVariationBase#setMetaInformation(IOvModelVariationBaseMetainformation)
     */
    @Override
    public void setMetaInformation(IOvModelVariationBaseMetainformation metaInformation) {
        this.metainformation = metaInformation;
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.IOvModelVariationBase#isOptional()
     */
    @Override
    public boolean isOptional() {
        return this.optional;
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.IOvModelVariationBase#setOptional(boolean)
     */
    @Override
    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.vmt.core.common.core.configuration.IConfigurable#isSelected()
     */
    @Override
    public boolean isSelected() {
        return this.selected;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.vmt.core.common.core.configuration.IConfigurable#setSelected(boolean)
     */
    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.configuration.IValidateInternal#isValid(boolean)
     */
    @Override
    public boolean isValid(boolean isMandatory) {
        return !isMandatory || this.isSelected();
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
        result = prime * result + (this.optional ? 1231 : 1237);
        result = prime * result + (this.metainformation == null ? 0 : this.metainformation.hashCode());
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
        OvModelVariationBase other = (OvModelVariationBase) obj;
        if (this.optional != other.optional) {
            return false;
        }
        if (this.metainformation == null) {
            if (other.metainformation != null) {
                return false;
            }
        } else if (!this.metainformation.equals(other.metainformation)) {
            return false;
        }
        return true;
    }

}
