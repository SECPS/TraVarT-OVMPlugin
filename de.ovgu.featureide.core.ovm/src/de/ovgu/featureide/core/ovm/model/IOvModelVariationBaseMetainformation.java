package de.ovgu.featureide.core.ovm.model;

import java.util.List;

import de.ovgu.featureide.core.ovm.model.constraint.IOvModelConstraint;
import de.ovgu.featureide.fm.core.base.IPropertyContainer;

/**
 * Manages all additional properties of a OVM.
 *
 */
public interface IOvModelVariationBaseMetainformation {

	IPropertyContainer getCustomProperties();

	String getDescription();

	void setDescription(String description);

	boolean isAbstract();

	void setAbstract(boolean isAbstract);

	boolean isHidden();

	void setHidden(boolean hidden);

	boolean isPartOfOvModelRoot();

	void setPartOfOvModelRoot(boolean partOfOvModelRoot);

	List<IOvModelConstraint> getReferencedConstraints();

	void setReferencedConstraints(List<IOvModelConstraint> referencedConstraints);

	@Override
	public int hashCode();

	@Override
	boolean equals(Object obj);
}
