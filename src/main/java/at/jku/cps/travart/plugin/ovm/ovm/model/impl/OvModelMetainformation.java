package at.jku.cps.travart.plugin.ovm.ovm.model.impl;

import at.jku.cps.travart.ovm.model.IOvModelMetainformation;
import de.ovgu.featureide.fm.core.base.IPropertyContainer;
import de.ovgu.featureide.fm.core.base.impl.MapPropertyContainer;

/**
 * Represents a concrete implementation of an {@link IOvModelMetainformation}.
 *
 * @author johannstoebich
 * @see IOvModelMetainformation
 */
public class OvModelMetainformation implements IOvModelMetainformation {

    protected IPropertyContainer customProperties;

    protected String description;

    public OvModelMetainformation() {
        this.customProperties = new MapPropertyContainer();
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.IOvModelMetainformation#getCustomProperties()
     */
    @Override
    public IPropertyContainer getCustomProperties() {
        return this.customProperties;
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.IOvModelMetainformation#getDescription()
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.IOvModelMetainformation#setDescription(String)
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
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
        OvModelMetainformation other = (OvModelMetainformation) obj;
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
