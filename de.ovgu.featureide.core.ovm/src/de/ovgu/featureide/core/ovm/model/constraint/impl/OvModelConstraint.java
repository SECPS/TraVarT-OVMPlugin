package de.ovgu.featureide.core.ovm.model.constraint.impl;

import de.ovgu.featureide.core.ovm.model.IIdentifyable;
import de.ovgu.featureide.core.ovm.model.IOvModelElement;
import de.ovgu.featureide.core.ovm.model.IOvModelVariationBase;
import de.ovgu.featureide.core.ovm.model.constraint.IOvModelConstraint;
import de.ovgu.featureide.core.ovm.model.constraint.IOvModelConstraintMetainformation;
import de.ovgu.featureide.core.ovm.model.impl.OvModelElement;

/**
 * TODO description
 *
 * @author johannstoebich
 */
public abstract class OvModelConstraint extends OvModelElement implements IOvModelConstraint {

	protected IOvModelConstraintMetainformation metanformation;

	protected IOvModelVariationBase source;

	protected IOvModelVariationBase target;

	public OvModelConstraint() {
		metanformation = new OvModelConstraintMetainformation();
	}

	/*
	 * (non-Javadoc)
	 * @see de.ovgu.featureide.core.ovm.model.constraint.IOvModelConstraint#getSource()
	 */
	@Override
	public IOvModelVariationBase getSource() {
		return source;
	}

	/*
	 * (non-Javadoc)
	 * @see de.ovgu.featureide.core.ovm.model.constraint.IOvModelConstraint#setSource(de.ovgu.featureide.core.ovm.model.IOvModelElement)
	 */
	@Override
	public void setSource(IOvModelVariationBase source) {
		this.source = source;
	}

	/*
	 * (non-Javadoc)
	 * @see de.ovgu.featureide.core.ovm.model.constraint.IOvModelConstraint#getTarget()
	 */
	@Override
	public IOvModelVariationBase getTarget() {
		return target;
	}

	/*
	 * (non-Javadoc)
	 * @see de.ovgu.featureide.core.ovm.model.constraint.IOvModelConstraint#setTarget(de.ovgu.featureide.core.ovm.model.IOvModelElement)
	 */
	@Override
	public void setTarget(IOvModelVariationBase target) {
		this.target = target;
	}

	/**
	 * @return the metaInformation
	 */
	@Override
	public IOvModelConstraintMetainformation getMetainformation() {
		return metanformation;
	}

	@Override
	public IOvModelElement getElement(IIdentifyable identifyable) {
		IOvModelElement element;
		if ((element = super.getElement(identifyable)) != null) {
			return element;
		} else if ((getSource() != null) && ((element = getSource().getElement(identifyable)) != null)) {
			return element;
		} else if ((getTarget() != null) && ((element = getTarget().getElement(identifyable)) != null)) {
			return element;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + ((metanformation == null) ? 0 : metanformation.hashCode());
		result = (prime * result) + ((source == null) ? 0 : source.hashCode());
		result = (prime * result) + ((target == null) ? 0 : target.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
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
		final OvModelConstraint other = (OvModelConstraint) obj;
		if (metanformation == null) {
			if (other.metanformation != null) {
				return false;
			}
		} else if (!metanformation.equals(other.metanformation)) {
			return false;
		}
		if (source == null) {
			if (other.source != null) {
				return false;
			}
		} else if (!source.equals(other.source)) {
			return false;
		}
		if (target == null) {
			if (other.target != null) {
				return false;
			}
		} else if (!target.equals(other.target)) {
			return false;
		}
		return true;
	}
}
