import de.ovgu.featureide.core.ovm.model.IIdentifiable;
import de.ovgu.featureide.core.ovm.model.IOvModel;
import de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint;
import de.ovgu.featureide.fm.core.functional.Functional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Legal Notice: Some of this code or comments are overtaken from the FeatrueIDE's {@link FeatureModel}.
 * <p>
 * Represents a concrete implementation of an {@link IOvModel}.
 *
 * @author johannstoebich
 * @see IOvModel
 */
public class OvModel extends Identifiable implements IOvModel {

    protected final String factoryId;
    protected final IOvModelMetainformation metainformation;
    protected final List<IOvModelVariationPoint> variationPoints = new ArrayList<>();
    protected final List<IOvModelConstraint> constraints = new ArrayList<>();
    protected String sourceFile;

    public OvModel(String factoryId) {
        super();
        this.factoryId = factoryId;
        this.metainformation = new OvModelMetainformation();
    }

    public OvModel(String factoryId, long id) {
        super(id);
        this.factoryId = factoryId;
        this.metainformation = new OvModelMetainformation();
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.IOvModel#getFactoryId()
     */
    @Override
    public String getFactoryId() {
        return this.factoryId;
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.IOvModel#getMetainformation()
     */
    @Override
    public IOvModelMetainformation getMetainformation() {
        return this.metainformation;
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.IOvModel#getSourceFile()
     */
    @Override
    public String getSourceFile() {
        return this.sourceFile;
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.IOvModel#setSourceFile(java.lang.String)
     */
    @Override
    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.IOvModel#getNumberOfVariationPoints()
     */
    @Override
    public int getNumberOfVariationPoints() {
        return this.variationPoints.size();
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.IOvModel#addVariationPoint(de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint)
     */
    @Override
    public boolean addVariationPoint(IOvModelVariationPoint variationPoint) {
        return this.variationPoints.add(variationPoint);
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.IOvModel#getVariationPoints()
     */
    @Override
    public List<IOvModelVariationPoint> getVariationPoints() {
        return Collections.unmodifiableList(this.variationPoints);
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.IOvModel#deleteVariationPoint(de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint)
     */
    @Override
    public boolean removeVariationPoint(IOvModelVariationPoint variationPoint) {
        return this.variationPoints.remove(variationPoint);
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.base.IOvmModel#addConstraint(de.ovgu.featureide.core.ovm.model.base.IOvmConstraint)
     */
    @Override
    public boolean addConstraint(IOvModelConstraint constraint) {
        return this.constraints.add(constraint);
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.base.IOvmModel#addConstraint(de.ovgu.featureide.core.ovm.model.base.IOvmConstraint, int)
     */
    @Override
    public void addConstraint(IOvModelConstraint constraint, int index) {
        this.constraints.add(index, constraint);
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.base.IOvmModel#getConstraintCount()
     */
    @Override
    public int getConstraintCount() {
        return this.constraints.size();
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.base.IOvmModel#getConstraintIndex(de.ovgu.featureide.core.ovm.model.base.IOvmConstraint)
     */
    @Override
    public int getConstraintIndex(IOvModelConstraint constraint) {
        return this.constraints.indexOf(constraint);
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.base.IOvmModel#getConstraints()
     */
    @Override
    public List<IOvModelConstraint> getConstraints() {
        return Collections.unmodifiableList(this.constraints);
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.base.IOvmModel#setConstraints(java.lang.Iterable)
     */
    @Override
    public void setConstraints(Iterable<IOvModelConstraint> constraints) {
        this.constraints.clear();
        this.constraints.addAll(Functional.toList(constraints));
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.base.IOvmModel#removeConstraint(de.ovgu.featureide.core.ovm.model.base.IOvmConstraint)
     */
    @Override
    public boolean removeConstraint(IOvModelConstraint constraint) {
        return this.constraints.remove(constraint);
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.base.IOvmModel#removeConstraint(int)
     */
    @Override
    public void removeConstraint(int index) {
        this.constraints.remove(index);
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.base.IOvmModel#replaceConstraint(de.ovgu.featureide.core.ovm.model.base.IOvmConstraint, int)
     */
    @Override
    public void replaceConstraint(IOvModelConstraint constraint, int index) {
        if (constraint == null) {
            throw new NullPointerException();
        }
        this.constraints.remove(this.constraints.get(index));
        this.constraints.set(index, constraint);
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.IOvModel#getElement(de.ovgu.featureide.core.ovm.model.IIdentifiable)
     */
    @Override
    public IOvModelElement getElement(IIdentifiable identifiable) {
        IOvModelElement element;
        for (IOvModelVariationPoint variationPoint : this.variationPoints) {
            element = variationPoint.getElement(identifiable);
            if (element != null) {
                return element;
            }
        }
        for (IOvModelConstraint constraint : this.constraints) {
            element = constraint.getElement(identifiable);
            if (element != null) {
                return element;
            }
        }
        return null;
    }

    /**
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = (prime * result) + ((this.constraints == null) ? 0 : this.constraints.hashCode());
        result = (prime * result) + ((this.metainformation == null) ? 0 : this.metainformation.hashCode());
        result = (prime * result) + ((this.variationPoints == null) ? 0 : this.variationPoints.hashCode());
        return result;
    }

    /**
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
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
        OvModel other = (OvModel) obj;
        if (this.constraints == null) {
            if (other.constraints != null) {
                return false;
            }
        } else if (!this.constraints.equals(other.constraints)) {
            return false;
        }
        if (this.metainformation == null) {
            if (other.metainformation != null) {
                return false;
            }
        } else if (!this.metainformation.equals(other.metainformation)) {
            return false;
        }
        if (this.variationPoints == null) {
            if (other.variationPoints != null) {
                return false;
            }
        } else if (!this.variationPoints.equals(other.variationPoints)) {
            return false;
        }
        return true;
    }

    /**
     * (non-Javadoc)
     *
     * @see IValidate#isValid()
     */
    @Override
    public boolean isValid() {
        boolean isValid = true;
        for (IOvModelVariationPoint variationPoint : this.variationPoints) {
            isValid = isValid && variationPoint.isValid(!variationPoint.isOptional());
        }
        for (IOvModelConstraint constraint : this.constraints) {
            isValid = isValid && constraint.isValid();
        }
        return isValid;
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.model.IOvModel#afterSelection()
     */
    @Override
    public void afterSelection() {
        /*
         * for (final IOvModelConstraint constraint : constraints) { if (constraint instanceof IOvModelExcludesConstraint) { final IOvModelExcludesConstraint
         * excludesConstraint = (IOvModelExcludesConstraint) constraint; if
         * (excludesConstraint.getSource().getName().startsWith(DefaultModelTransformerProperties.CONSTRAINT_VARIATION_POINT_PREFIX)) {
         * excludesConstraint.getSource().setSelected(!excludesConstraint.getTarget().isSelected()); } if
         * (excludesConstraint.getTarget().getName().startsWith(DefaultModelTransformerProperties.CONSTRAINT_VARIATION_POINT_PREFIX)) {
         * excludesConstraint.getTarget().setSelected(!excludesConstraint.getSource().isSelected()); } } }
         */

        Set<IConfigurable> configurables = OvModelUtils.getIConfigurable(this).keySet();
        Set<IConfigurable> virtualVariationsPoints = new HashSet<IConfigurable>();
        for (IConfigurable configurable : configurables) {
            if (configurable.getName().startsWith(DefaultModelTransformerProperties.CONSTRAINT_VARIATION_POINT_PREFIX)) {
                virtualVariationsPoints.add(configurable);
            }
        }
        for (int i = 0; i < Math.pow(2, virtualVariationsPoints.size()); i++) {
            String bin = Integer.toBinaryString(i);

            while (bin.length() < virtualVariationsPoints.size()) {
                bin = bin + "0";
            }
            System.out.println("Check configuratoin " + bin + ".");
            this.applyConfiguration(virtualVariationsPoints, bin);

            if (this.isValid()) {
                return;
            }
        }
    }

    /**
     * This method applys an binary configuration to the set of configuration.
     *
     * @param configuration
     * @param bin
     */
    private void applyConfiguration(Set<IConfigurable> configurations, String bin) {
        if (configurations.size() != bin.length()) {
            throw new IllegalArgumentException("Lenghts must be of the same size.");
        }

        int i = 0;
        for (IConfigurable configuration : configurations) {
            configuration.setSelected(bin.charAt(i) == '1');
            i++;
        }
    }

}
