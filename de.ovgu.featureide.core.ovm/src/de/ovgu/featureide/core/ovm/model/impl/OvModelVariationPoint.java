package de.ovgu.featureide.core.ovm.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.ovgu.featureide.core.ovm.model.IIdentifiable;
import de.ovgu.featureide.core.ovm.model.IOvModelElement;
import de.ovgu.featureide.core.ovm.model.IOvModelVariationBase;
import de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint;
import de.ovgu.featureide.fm.core.functional.Functional;

/**
 * Represents a concrete implementation of an {@link IOvModelVariationPoint}.
 *
 * @see IOvModelVariationPoint
 *
 * @author johannstoebich
 */
public class OvModelVariationPoint extends OvModelVariationBase implements IOvModelVariationPoint {

	private static final int EMPTY_VALUE = -1;

	protected boolean alternative;

	protected int minChoices;

	protected int maxChoices;

	protected final List<IOvModelVariationBase> mandatoryChildren = new ArrayList<>();

	protected final List<IOvModelVariationBase> optionalChildren = new ArrayList<>();

	public OvModelVariationPoint() {
		minChoices = EMPTY_VALUE;
		maxChoices = EMPTY_VALUE;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint#isAlternative()
	 */
	@Override
	public boolean isAlternative() {
		return alternative;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint#setAlternative(boolean)
	 */
	@Override
	public void setAlternative(boolean alternative) {
		this.alternative = alternative;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint#hasMinChoices()
	 */
	@Override
	public boolean hasMinChoices() {
		return minChoices != EMPTY_VALUE;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint#getMinChoices()
	 */
	@Override
	public int getMinChoices() {
		return minChoices;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint#setMinChoices(int)
	 */
	@Override
	public void setMinChoices(int minChoices) {
		this.minChoices = minChoices;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint#hasMaxChoices()
	 */
	@Override
	public boolean hasMaxChoices() {
		return maxChoices != EMPTY_VALUE;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint#getMaxChoices()
	 */
	@Override
	public int getMaxChoices() {
		return maxChoices;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint#setMaxChoices(int)
	 */
	@Override
	public void setMaxChoices(int maxChoices) {
		this.maxChoices = maxChoices;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint#hasMandatoryChildren()
	 */
	@Override
	public boolean hasMandatoryChildren() {
		return mandatoryChildren.size() > 0;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint#getMandatoryChildrenCount()
	 */
	@Override
	public int getMandatoryChildrenCount() {
		return mandatoryChildren.size();
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint#getMandatoryChildren()
	 */
	@Override
	public List<IOvModelVariationBase> getMandatoryChildren() {
		return Collections.unmodifiableList(mandatoryChildren);
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint#addMandatoryChild(de.ovgu.featureide.core.ovm.model.IOvModelVariationBase)
	 */
	@Override
	public boolean addMandatoryChild(IOvModelVariationBase mandatoryChild) {
		return mandatoryChildren.add(mandatoryChild);
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint#removeMandatoryChild(de.ovgu.featureide.core.ovm.model.IOvModelVariationBase)
	 */
	@Override
	public boolean removeMandatoryChild(IOvModelVariationBase mandatoryChild) {
		return mandatoryChildren.remove(mandatoryChild);
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint#setMandatoryChildren(java.util.List)
	 */
	@Override
	public void setMandatoryChildren(List<IOvModelVariationBase> mandatoryChildren) {
		this.mandatoryChildren.clear();
		this.mandatoryChildren.addAll(Functional.toList(mandatoryChildren));
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint#hasOptionalChildren()
	 */
	@Override
	public boolean hasOptionalChildren() {
		return optionalChildren.size() > 0;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint#getOptionalChildrenCount()
	 */
	@Override
	public int getOptionalChildrenCount() {
		return optionalChildren.size();
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint#getOptionalChildren()
	 */
	@Override
	public List<IOvModelVariationBase> getOptionalChildren() {
		return Collections.unmodifiableList(optionalChildren);
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint#addOptionalChild(de.ovgu.featureide.core.ovm.model.IOvModelVariationBase)
	 */
	@Override
	public boolean addOptionalChild(IOvModelVariationBase optionalChild) {
		return optionalChildren.add(optionalChild);
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint#removeOptionalChild(de.ovgu.featureide.core.ovm.model.IOvModelVariationBase)
	 */
	@Override
	public boolean removeOptionalChild(IOvModelVariationBase optionalChild) {
		return optionalChildren.remove(optionalChild);
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint#setOptionalChildren(java.util.List)
	 */
	@Override
	public void setOptionalChildren(List<IOvModelVariationBase> optionalChildren) {
		this.optionalChildren.clear();
		this.optionalChildren.addAll(Functional.toList(optionalChildren));
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see de.ovgu.featureide.core.ovm.model.IOvModelElement#getElement(de.ovgu.featureide.core.ovm.model.IIdentifiable)
	 */
	@Override
	public IOvModelElement getElement(IIdentifiable identifiable) {
		IOvModelElement element = super.getElement(identifiable);
		if (element != null) {
			return element;
		}

		for (final IOvModelVariationBase mandatoryChild : mandatoryChildren) {
			element = mandatoryChild.getElement(identifiable);
			if (element != null) {
				return element;
			}
		}

		for (final IOvModelVariationBase optionalChild : optionalChildren) {
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
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + (alternative ? 1231 : 1237);
		result = (prime * result) + ((mandatoryChildren == null) ? 0 : mandatoryChildren.hashCode());
		result = (prime * result) + maxChoices;
		result = (prime * result) + minChoices;
		result = (prime * result) + ((optionalChildren == null) ? 0 : optionalChildren.hashCode());
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
		final OvModelVariationPoint other = (OvModelVariationPoint) obj;
		if (alternative != other.alternative) {
			return false;
		}
		if (mandatoryChildren == null) {
			if (other.mandatoryChildren != null) {
				return false;
			}
		} else if (!mandatoryChildren.equals(other.mandatoryChildren)) {
			return false;
		}
		if (maxChoices != other.maxChoices) {
			return false;
		}
		if (minChoices != other.minChoices) {
			return false;
		}
		if (optionalChildren == null) {
			if (other.optionalChildren != null) {
				return false;
			}
		} else if (!optionalChildren.equals(other.optionalChildren)) {
			return false;
		}
		return true;
	}

}
