package at.jku.cps.travart.plugin.ovm.ovm.model.impl;

import at.jku.cps.travart.plugin.ovm.ovm.model.IIdentifiable;

/**
 * Represents a concrete implementation of an {@link IIdentifiable}.
 *
 * @author johannstoebich
 * @see IIdentifiable
 */
public abstract class Identifiable implements IIdentifiable {

    private static long NEXT_ID = 0;

    protected long internalId;
    protected String name;

    public Identifiable() {
        this.internalId = getNextId();
    }

    public Identifiable(long internalId) {
        this.internalId = internalId;
    }

    protected static final synchronized long getNextId() {
        return NEXT_ID++;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IIdentifiable#getInternalId()
     */
    @Override
    public long getInternalId() {
        return this.internalId;
    }

    public void setInternalId(long internalId) {
        this.internalId = internalId;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IIdentifiable#getName()
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IIdentifiable#setName(String)
     */
    @Override
    public void setName(String name) {
        this.name = name;
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
        result = prime * result + (this.name == null ? 0 : this.name.hashCode());
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
        Identifiable other = (Identifiable) obj;
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
