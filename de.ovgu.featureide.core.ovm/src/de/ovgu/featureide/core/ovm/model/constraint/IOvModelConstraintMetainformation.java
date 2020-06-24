package de.ovgu.featureide.core.ovm.model.constraint;

import de.ovgu.featureide.fm.core.base.IPropertyContainer;

/**
 * Manages all additional properties of a OVM.
 *
 */
public interface IOvModelConstraintMetainformation {

	IPropertyContainer getCustomProperties();

	String getDescription();

	void setDescription(String description);

	@Override
	public int hashCode();

	@Override
	boolean equals(Object obj);
}
