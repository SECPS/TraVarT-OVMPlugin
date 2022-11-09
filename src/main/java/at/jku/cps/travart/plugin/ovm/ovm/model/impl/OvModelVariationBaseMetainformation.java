package at.jku.cps.travart.plugin.ovm.ovm.model.impl;

import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationBaseMetainformation;
import at.jku.cps.travart.plugin.ovm.ovm.model.constraint.IOvModelConstraint;
import de.ovgu.featureide.fm.core.base.IPropertyContainer;
import de.ovgu.featureide.fm.core.base.impl.MapPropertyContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a concrete implementation of an
 * {@link IOvModelVariationBaseMetainformation}.
 *
 * @author johannstoebich
 * @see IOvModelVariationBaseMetainformation
 */
public class OvModelVariationBaseMetainformation implements IOvModelVariationBaseMetainformation {

    protected boolean isAbstract;
    protected boolean hidden;
    protected boolean partOfOvModelRoot;
    protected String description;
    protected List<IOvModelConstraint> referencedConstraints;
    protected IPropertyContainer customProperties;

    public OvModelVariationBaseMetainformation() {
        this.referencedConstraints = new ArrayList<IOvModelConstraint>();
        this.customProperties = new MapPropertyContainer();
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationBaseMetainformation#getCustomProperties()
     */
    @Override
    public IPropertyContainer getCustomProperties() {
        return this.customProperties;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationBaseMetainformation#getDescription()
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationBaseMetainformation#setDescription(String)
     */
    @Override
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationBaseMetainformation#isAbstract()
     */
    @Override
    public boolean isAbstract() {
        return this.isAbstract;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationBaseMetainformation#setAbstract(boolean)
     */
    @Override
    public void setAbstract(final boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationBaseMetainformation#isHidden()
     */
    @Override
    public boolean isHidden() {
        return this.hidden;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationBaseMetainformation#setHidden(boolean)
     */
    @Override
    public void setHidden(final boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationBaseMetainformation#isPartOfOvModelRoot()
     */
    @Override
    public boolean isPartOfOvModelRoot() {
        return this.partOfOvModelRoot;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationBaseMetainformation#setPartOfOvModelRoot(boolean)
     */
    @Override
    public void setPartOfOvModelRoot(final boolean partOfOvModelRoot) {
        this.partOfOvModelRoot = partOfOvModelRoot;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationBaseMetainformation#getReferencedConstraints()
     */
    @Override
    public List<IOvModelConstraint> getReferencedConstraints() {
        return this.referencedConstraints;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationBaseMetainformation#setReferencedConstraints(List)
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
        final OvModelVariationBaseMetainformation other = (OvModelVariationBaseMetainformation) obj;
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
        if (this.partOfOvModelRoot != other.partOfOvModelRoot) {
            return false;
        }
        return true;
    }

}
