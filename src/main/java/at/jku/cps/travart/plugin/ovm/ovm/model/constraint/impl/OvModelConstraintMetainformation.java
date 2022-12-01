package at.jku.cps.travart.plugin.ovm.ovm.model.constraint.impl;

import at.jku.cps.travart.plugin.ovm.ovm.model.constraint.IOvModelConstraintMetainformation;

/**
 * Represents a concrete implementation of an
 * {@link IOvModelConstraintMetainformation}.
 *
 * @author johannstoebich
 * @see IOvModelConstraintMetainformation
 */
public class OvModelConstraintMetainformation implements IOvModelConstraintMetainformation {

    protected String description;

    public OvModelConstraintMetainformation() {
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.constraint.IOvModelConstraintMetainformation#getDescription()
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.constraint.IOvModelConstraintMetainformation#setDescription(String)
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
        final OvModelConstraintMetainformation other = (OvModelConstraintMetainformation) obj;
        if (this.description == null) {
            return other.description == null;
        } else if (!this.description.equals(other.description)) {
            return false;
        }
        return true;
    }

}
