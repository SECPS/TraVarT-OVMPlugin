package de.ovgu.featureide.core.ovm.model;

/**
 * TODO description
 *
 * @author johannstoebich
 */
public abstract interface IOvModelElement extends IIdentifiable {

	IOvModelElement getElement(IIdentifiable identifiable);
}
