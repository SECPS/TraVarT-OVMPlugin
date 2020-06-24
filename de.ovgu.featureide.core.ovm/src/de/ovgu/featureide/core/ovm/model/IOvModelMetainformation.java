package de.ovgu.featureide.core.ovm.model;

import de.ovgu.featureide.fm.core.base.IPropertyContainer;

/**
 * Manages all additional properties of a OVM.
 *
 */
public interface IOvModelMetainformation {

	IPropertyContainer getCustomProperties();

	String getDescription();

	void setDescription(String description);

}
