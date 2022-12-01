package at.jku.cps.travart.plugin.ovm.ovm.model.impl;

import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationBaseMetaInformation;
import at.jku.cps.travart.plugin.ovm.ovm.model.constraint.IOvModelConstraint;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a concrete implementation of an
 * {@link IOvModelVariationBaseMetaInformation}.
 *
 * @author johannstoebich
 * @see IOvModelVariationBaseMetaInformation
 */
public class OvModelVariationBaseMetaInformation implements IOvModelVariationBaseMetaInformation {

    protected boolean isAbstract;
    protected boolean hidden;
    protected boolean partOfOvModelRoot;
    protected String description;
    protected List<IOvModelConstraint> referencedConstraints;

    public OvModelVariationBaseMetaInformation() {
        this.referencedConstraints = new ArrayList<>();
    }

    /**
     * (non-Javadoc)
     *
     * @see IOvModelVariationBaseMetaInformation#getDescription()
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * (non-Javadoc)
     *
     * @see IOvModelVariationBaseMetaInformation#setDescription(String)
     */
    @Override
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * (non-Javadoc)
     *
     * @see IOvModelVariationBaseMetaInformation#isAbstract()
     */
    @Override
    public boolean isAbstract() {
        return this.isAbstract;
    }

    /**
     * (non-Javadoc)
     *
     * @see IOvModelVariationBaseMetaInformation#setAbstract(boolean)
     */
    @Override
    public void setAbstract(final boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

    /**
     * (non-Javadoc)
     *
     * @see IOvModelVariationBaseMetaInformation#isHidden()
     */
    @Override
    public boolean isHidden() {
        return this.hidden;
    }

    /**
     * (non-Javadoc)
     *
     * @see IOvModelVariationBaseMetaInformation#setHidden(boolean)
     */
    @Override
    public void setHidden(final boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * (non-Javadoc)
     *
     * @see IOvModelVariationBaseMetaInformation#isPartOfOvModelRoot()
     */
    @Override
    public boolean isPartOfOvModelRoot() {
        return this.partOfOvModelRoot;
    }

    /**
     * (non-Javadoc)
     *
     * @see IOvModelVariationBaseMetaInformation#setPartOfOvModelRoot(boolean)
     */
    @Override
    public void setPartOfOvModelRoot(final boolean partOfOvModelRoot) {
        this.partOfOvModelRoot = partOfOvModelRoot;
    }

    /**
     * (non-Javadoc)
     *
     * @see IOvModelVariationBaseMetaInformation#getReferencedConstraints()
     */
    @Override
    public List<IOvModelConstraint> getReferencedConstraints() {
        return this.referencedConstraints;
    }

    /**
     * (non-Javadoc)
     *
     * @see IOvModelVariationBaseMetaInformation#setReferencedConstraints(List)
     */
    @Override
    public void setReferencedConstraints(final List<IOvModelConstraint> referencedConstraints) {
        this.referencedConstraints = referencedConstraints;
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
        result = prime * result + (this.hidden ? 1231 : 1237);
        result = prime * result + (this.isAbstract ? 1231 : 1237);
        result = prime * result + (this.partOfOvModelRoot ? 1231 : 1237);
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
        final OvModelVariationBaseMetaInformation other = (OvModelVariationBaseMetaInformation) obj;
        if (this.description == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!this.description.equals(other.description)) {
            return false;
        }
        if (this.hidden != other.hidden) {
            return false;
        }
        if (this.isAbstract != other.isAbstract) {
            return false;
        }
        return this.partOfOvModelRoot == other.partOfOvModelRoot;
    }

}
