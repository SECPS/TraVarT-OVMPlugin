package de.ovgu.featureide.core.ovm.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.ovgu.featureide.core.ovm.model.IIdentifiable;
import de.ovgu.featureide.core.ovm.model.IOvModel;
import de.ovgu.featureide.core.ovm.model.IOvModelElement;
import de.ovgu.featureide.core.ovm.model.IOvModelMetainformation;
import de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint;
import de.ovgu.featureide.core.ovm.model.constraint.IOvModelConstraint;
import de.ovgu.featureide.fm.core.functional.Functional;

/**
 * Legal Notice: Some of this code or comments are overtaken from the FeatrueIDE's {@link FeatureModel}.
 *
 * Represents a concrete implementation of an {@link IOvModel}.
 *
 * @see IOvModel
 *
 * @author johannstoebich
 */
public class OvModel extends Identifiable implements IOvModel {

	protected final String factoryId;

	protected String sourceFile;

	protected final IOvModelMetainformation metainformation;

	protected final List<IOvModelVariationPoint> variationPoints = new ArrayList<>();

	protected final List<IOvModelConstraint> constraints = new ArrayList<>();

	public OvModel(String factoryId) {
		super();
		this.factoryId = factoryId;
		metainformation = new OvModelMetainformation();
	}

	public OvModel(String factoryId, long id) {
		super(id);
		this.factoryId = factoryId;
		metainformation = new OvModelMetainformation();
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.IOvModel#getFactoryId()
	 */
	@Override
	public String getFactoryId() {
		return factoryId;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.IOvModel#getMetainformation()
	 */
	@Override
	public IOvModelMetainformation getMetainformation() {
		return metainformation;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.IOvModel#getSourceFile()
	 */
	@Override
	public String getSourceFile() {
		return sourceFile;
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
		return variationPoints.size();
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.IOvModel#addVariationPoint(de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint)
	 */
	@Override
	public boolean addVariationPoint(IOvModelVariationPoint variationPoint) {
		return variationPoints.add(variationPoint);
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.IOvModel#getVariationPoints()
	 */
	@Override
	public List<IOvModelVariationPoint> getVariationPoints() {
		return Collections.unmodifiableList(variationPoints);
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.IOvModel#deleteVariationPoint(de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint)
	 */
	@Override
	public boolean removeVariationPoint(IOvModelVariationPoint variationPoint) {
		return variationPoints.remove(variationPoint);
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.base.IOvmModel#addConstraint(de.ovgu.featureide.core.ovm.model.base.IOvmConstraint)
	 */
	@Override
	public boolean addConstraint(IOvModelConstraint constraint) {
		return constraints.add(constraint);
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.base.IOvmModel#addConstraint(de.ovgu.featureide.core.ovm.model.base.IOvmConstraint, int)
	 */
	@Override
	public void addConstraint(IOvModelConstraint constraint, int index) {
		constraints.add(index, constraint);
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.base.IOvmModel#getConstraintCount()
	 */
	@Override
	public int getConstraintCount() {
		return constraints.size();
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.base.IOvmModel#getConstraintIndex(de.ovgu.featureide.core.ovm.model.base.IOvmConstraint)
	 */
	@Override
	public int getConstraintIndex(IOvModelConstraint constraint) {
		return constraints.indexOf(constraint);
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.base.IOvmModel#getConstraints()
	 */
	@Override
	public List<IOvModelConstraint> getConstraints() {
		return Collections.unmodifiableList(constraints);
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.base.IOvmModel#removeConstraint(de.ovgu.featureide.core.ovm.model.base.IOvmConstraint)
	 */
	@Override
	public boolean removeConstraint(IOvModelConstraint constraint) {
		return constraints.remove(constraint);
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.base.IOvmModel#removeConstraint(int)
	 */
	@Override
	public void removeConstraint(int index) {
		constraints.remove(index);
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
		constraints.remove(constraints.get(index));
		constraints.set(index, constraint);
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
	 * @see de.ovgu.featureide.core.ovm.model.IOvModel#getElement(de.ovgu.featureide.core.ovm.model.IIdentifiable)
	 */
	@Override
	public IOvModelElement getElement(IIdentifiable identifiable) {
		IOvModelElement element;
		for (final IOvModelVariationPoint variationPoint : variationPoints) {
			element = variationPoint.getElement(identifiable);
			if (element != null) {
				return element;
			}
		}
		for (final IOvModelConstraint constraint : constraints) {
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
		result = (prime * result) + ((constraints == null) ? 0 : constraints.hashCode());
		result = (prime * result) + ((metainformation == null) ? 0 : metainformation.hashCode());
		result = (prime * result) + ((variationPoints == null) ? 0 : variationPoints.hashCode());
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
		if (getClass() != obj.getClass()) {
			return false;
		}
		final OvModel other = (OvModel) obj;
		if (constraints == null) {
			if (other.constraints != null) {
				return false;
			}
		} else if (!constraints.equals(other.constraints)) {
			return false;
		}
		if (metainformation == null) {
			if (other.metainformation != null) {
				return false;
			}
		} else if (!metainformation.equals(other.metainformation)) {
			return false;
		}
		if (variationPoints == null) {
			if (other.variationPoints != null) {
				return false;
			}
		} else if (!variationPoints.equals(other.variationPoints)) {
			return false;
		}
		return true;
	}

}
