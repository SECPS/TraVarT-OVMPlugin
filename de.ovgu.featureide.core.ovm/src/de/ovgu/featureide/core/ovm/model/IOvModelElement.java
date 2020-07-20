package de.ovgu.featureide.core.ovm.model;

/**
 * This is an base class for all elements. Elements are constraints, variants or variation points.
 *
 * @author johannstoebich
 */
public abstract interface IOvModelElement extends IIdentifiable {

	/**
	 * This method searches for an element identified by an identifiable. If the name of the identifiable matches the constraint's, variation point's or
	 * variant's name it is returned.
	 *
	 * @param identifiable the identifiable which should be found.
	 * @return the element which should be found, otherwise <code>null</code>.
	 */
	IOvModelElement getElement(IIdentifiable identifiable);
}
