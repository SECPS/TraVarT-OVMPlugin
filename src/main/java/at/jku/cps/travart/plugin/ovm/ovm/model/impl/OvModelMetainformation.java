package at.jku.cps.travart.plugin.ovm.ovm.model.impl;

import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelMetainformation;

/**
 * Represents a concrete implementation of an {@link IOvModelMetainformation}.
 *
 * @author johannstoebich
 * @see IOvModelMetainformation
 */
public class OvModelMetainformation implements IOvModelMetainformation {

    protected String description;

    public OvModelMetainformation() {
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelMetainformation#getDescription()
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelMetainformation#setDescription(String)
     */
    @Override
    public void setDescription(final String description) {
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
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final OvModelMetainformation other = (OvModelMetainformation) obj;
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
