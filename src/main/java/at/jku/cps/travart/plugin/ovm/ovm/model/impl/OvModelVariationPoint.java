package at.jku.cps.travart.plugin.ovm.ovm.model.impl;

import at.jku.cps.travart.plugin.ovm.ovm.model.IIdentifiable;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelElement;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationBase;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationPoint;
import at.jku.cps.travart.plugin.ovm.ovm.transformation.DefaultOvModelTransformationProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a concrete implementation of an {@link IOvModelVariationPoint}.
 *
 * @author johannstoebich
 * @see IOvModelVariationPoint
 */
public class OvModelVariationPoint extends OvModelVariationBase implements IOvModelVariationPoint {

    public static final int EMPTY_VALUE = -1;
    protected final List<IOvModelVariationBase> mandatoryChildren = new ArrayList<>();
    protected final List<IOvModelVariationBase> optionalChildren = new ArrayList<>();
    protected boolean alternative;
    protected int minChoices;
    protected int maxChoices;

    public OvModelVariationPoint() {
        this.minChoices = EMPTY_VALUE;
        this.maxChoices = EMPTY_VALUE;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationPoint#isAlternative()
     */
    @Override
    public boolean isAlternative() {
        return this.alternative;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationPoint#setAlternative(boolean)
     */
    @Override
    public void setAlternative(final boolean alternative) {
        this.alternative = alternative;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationPoint#hasMinChoices()
     */
    @Override
    public boolean hasMinChoices() {
        return this.minChoices != EMPTY_VALUE;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationPoint#getMinChoices()
     */
    @Override
    public int getMinChoices() {
        return this.minChoices;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationPoint#setMinChoices(int)
     */
    @Override
    public void setMinChoices(final int minChoices) {
        this.minChoices = minChoices;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationPoint#hasMaxChoices()
     */
    @Override
    public boolean hasMaxChoices() {
        return this.maxChoices != EMPTY_VALUE;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationPoint#getMaxChoices()
     */
    @Override
    public int getMaxChoices() {
        return this.maxChoices;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationPoint#setMaxChoices(int)
     */
    @Override
    public void setMaxChoices(final int maxChoices) {
        this.maxChoices = maxChoices;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationPoint#hasMandatoryChildren()
     */
    @Override
    public boolean hasMandatoryChildren() {
        return this.mandatoryChildren.size() > 0;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationPoint#getMandatoryChildrenCount()
     */
    @Override
    public int getMandatoryChildrenCount() {
        return this.mandatoryChildren.size();
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationPoint#getMandatoryChildren()
     */
    @Override
    public List<IOvModelVariationBase> getMandatoryChildren() {
        return Collections.unmodifiableList(this.mandatoryChildren);
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationPoint#setMandatoryChildren(List)
     */
    @Override
    public void setMandatoryChildren(final List<IOvModelVariationBase> mandatoryChildren) {
        this.mandatoryChildren.clear();
        this.mandatoryChildren.addAll(mandatoryChildren);
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationPoint#addMandatoryChild(at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationBase)
     */
    @Override
    public boolean addMandatoryChild(final IOvModelVariationBase mandatoryChild) {
        return this.mandatoryChildren.add(mandatoryChild);
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationPoint#removeMandatoryChild(at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationBase)
     */
    @Override
    public boolean removeMandatoryChild(final IOvModelVariationBase mandatoryChild) {
        return this.mandatoryChildren.remove(mandatoryChild);
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationPoint#hasOptionalChildren()
     */
    @Override
    public boolean hasOptionalChildren() {
        return this.optionalChildren.size() > 0;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationPoint#getOptionalChildrenCount()
     */
    @Override
    public int getOptionalChildrenCount() {
        return this.optionalChildren.size();
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationPoint#getOptionalChildren()
     */
    @Override
    public List<IOvModelVariationBase> getOptionalChildren() {
        return Collections.unmodifiableList(this.optionalChildren);
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationPoint#setOptionalChildren(List)
     */
    @Override
    public void setOptionalChildren(final List<IOvModelVariationBase> optionalChildren) {
        this.optionalChildren.clear();
        this.optionalChildren.addAll(optionalChildren);
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationPoint#addOptionalChild(at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationBase)
     */
    @Override
    public boolean addOptionalChild(final IOvModelVariationBase optionalChild) {
        return this.optionalChildren.add(optionalChild);
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationPoint#removeOptionalChild(at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationBase)
     */
    @Override
    public boolean removeOptionalChild(final IOvModelVariationBase optionalChild) {
        return this.optionalChildren.remove(optionalChild);
    }

    /**
     * (non-Javadoc)
     */
    @Override
    public void setSelected(final boolean selected) {
        super.setSelected(selected);
        if (this.mandatoryChildren.size() == 1) {
            final IOvModelVariationBase mandatoryChild = this.mandatoryChildren.get(0);
            final String mandatoryChildName = mandatoryChild.getName();
            if (mandatoryChildName.contains(DefaultOvModelTransformationProperties.VARIANT_PREFIX)
                    && mandatoryChildName.endsWith(this.getName())) {
                mandatoryChild.setSelected(selected);
            }
        }
    }

    /**
     * (non-Javadoc)
     */
    @Override
    public boolean isValid(final boolean isMandatory) {
        boolean isValid = super.isValid(isMandatory);
        if (isValid && this.isSelected()) { // execute check only if the variation point is selected
            // Shortcut (do not check artificial variants)
            if (this.mandatoryChildren.size() == 1 && this.mandatoryChildren.get(0) != null
                    && this.mandatoryChildren.get(0).getName() != null
                    && (this.mandatoryChildren.get(0).getName().startsWith(DefaultOvModelTransformationProperties.VARIANT_PREFIX)
                    || this.mandatoryChildren.get(0).getName()
                    .startsWith(DefaultOvModelTransformationProperties.CONSTRAINT_VARIATION_POINT_PREFIX
                            + DefaultOvModelTransformationProperties.VARIANT_PREFIX))
                    && this.mandatoryChildren.get(0).getName().endsWith(this.getName())) {
                return isValid;
            }
            // all mandatory children have to be checked
            for (final IOvModelVariationBase mandatoryChild : this.mandatoryChildren) {
                isValid = isValid && mandatoryChild.isSelected();
                if (!isValid) {
                    return false;
                }
            }

            final long mandatoryCount = this.mandatoryChildren.stream().filter(IOvModelVariationBase::isSelected).count();
            final long optionalCount = this.optionalChildren.stream().filter(IOvModelVariationBase::isSelected).count();
            final long selected = mandatoryCount + optionalCount;
            // check the ranges
            if (this.isAlternative()) {
                if (selected < this.minChoices || selected > this.maxChoices) {
                    isValid = false;
                }
                isValid = isValid && this.minChoices != EMPTY_VALUE && this.maxChoices != EMPTY_VALUE;
            } else {
                isValid = isValid && (this.minChoices == EMPTY_VALUE && this.maxChoices == EMPTY_VALUE
                        || this.minChoices <= mandatoryCount && this.maxChoices <= selected);
            }
            if (!isValid) {
                return false;
            }

            // check mandatory children
            for (final IOvModelVariationBase mandatoryChild : this.mandatoryChildren) {
                isValid = isValid && mandatoryChild.isValid(true);
                if (!isValid) {
                    return false;
                }
            }

            // check optional children
            for (final IOvModelVariationBase optionalChild : this.optionalChildren) {
                isValid = isValid && optionalChild.isValid(false);
                if (!isValid) {
                    return false;
                }
            }

            isValid = isValid && this.optionalChildren.size() + this.mandatoryChildren.size() > 0;
        } else if (isValid) {
            // Shortcut (do not check artificial variants)
            if (this.mandatoryChildren.size() == 1 && this.mandatoryChildren.get(0) != null
                    && this.mandatoryChildren.get(0).getName() != null
                    && (this.mandatoryChildren.get(0).getName().startsWith(DefaultOvModelTransformationProperties.VARIANT_PREFIX)
                    || this.mandatoryChildren.get(0).getName()
                    .startsWith(DefaultOvModelTransformationProperties.CONSTRAINT_VARIATION_POINT_PREFIX
                            + DefaultOvModelTransformationProperties.VARIANT_PREFIX))
                    && this.mandatoryChildren.get(0).getName().endsWith(this.getName())) {
                return isValid;
            }
            if (this.getName().contains(DefaultOvModelTransformationProperties.CONSTRAINT_VARIATION_POINT_PREFIX)) {
                return isValid;
            }
            // all mandatory children have to be checked
            for (final IOvModelVariationBase mandatoryChild : this.mandatoryChildren) {
                isValid = isValid && !mandatoryChild.isSelected();
                if (!isValid) {
                    return false;
                }
            }
            for (final IOvModelVariationBase optionalChild : this.optionalChildren) {
                isValid = isValid && !optionalChild.isSelected();
                if (!isValid) {
                    return false;
                }
            }
        }
        return isValid;
    }

    /**
     * (non-Javadoc)
     */
    @Override
    public IOvModelElement getElement(final IIdentifiable identifiable) {
        IOvModelElement element = super.getElement(identifiable);
        if (element != null) {
            return element;
        }

        for (final IOvModelVariationBase mandatoryChild : this.mandatoryChildren) {
            element = mandatoryChild.getElement(identifiable);
            if (element != null) {
                return element;
            }
        }

        for (final IOvModelVariationBase optionalChild : this.optionalChildren) {
            element = optionalChild.getElement(identifiable);
            if (element != null) {
                return element;
            }
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
        result = prime * result + (this.alternative ? 1231 : 1237);
        result = prime * result + (this.mandatoryChildren == null ? 0 : this.mandatoryChildren.hashCode());
        result = prime * result + this.maxChoices;
        result = prime * result + this.minChoices;
        result = prime * result + (this.optionalChildren == null ? 0 : this.optionalChildren.hashCode());
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
        if (!super.equals(obj)) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final OvModelVariationPoint other = (OvModelVariationPoint) obj;
        if (this.alternative != other.alternative) {
            return false;
        }
        if (this.mandatoryChildren == null) {
            if (other.mandatoryChildren != null) {
                return false;
            }
        } else if (!this.mandatoryChildren.equals(other.mandatoryChildren)) {
            return false;
        }
        if (this.maxChoices != other.maxChoices) {
            return false;
        }
        if (this.minChoices != other.minChoices) {
            return false;
        }
        if (this.optionalChildren == null) {
            if (other.optionalChildren != null) {
                return false;
            }
        } else if (!this.optionalChildren.equals(other.optionalChildren)) {
            return false;
        }
        return true;
    }

}
