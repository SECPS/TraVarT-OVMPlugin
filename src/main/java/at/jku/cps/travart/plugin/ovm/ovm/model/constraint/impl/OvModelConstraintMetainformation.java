package at.jku.cps.travart.plugin.ovm.ovm.model.constraint.impl;

import at.jku.cps.travart.ovm.model.constraint.IOvModelConstraintMetainformation;
import de.ovgu.featureide.fm.core.base.IPropertyContainer;
import de.ovgu.featureide.fm.core.base.impl.MapPropertyContainer;

/**
 * Represents a concrete implementation of an
 * {@link IOvModelConstraintMetainformation}.
 *
 * @author johannstoebich
 * @see IOvModelConstraintMetainformation
 */
public class OvModelConstraintMetainformation implements IOvModelConstraintMetainformation {

    protected String description;
    protected IPropertyContainer customProperties;

    public OvModelConstraintMetainformation() {
        this.customProperties = new MapPropertyContainer();
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.constraint.IOvModelConstraintMetainformation#getDescription()
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.constraint.IOvModelConstraintMetainformation#setDescription(String)
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.IOvModelConstraintMetainformation#getCustomProperties()
     */
    @Override
    public IPropertyContainer getCustomProperties() {
        return this.customProperties;
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.description == null ? 0 : this.description.hashCode());
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
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        OvModelConstraintMetainformation other = (OvModelConstraintMetainformation) obj;
        if (this.description == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!this.description.equals(other.description)) {
            return false;
        }
        return true;
    }

}
