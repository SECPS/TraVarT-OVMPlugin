package de.ovgu.featureide.core.ovm.model;

/**
 * TODO description
 *
 * @author johannstoebich
 */
public abstract interface IOvModelElement extends IIdentifyable {

	IOvModelElement getElement(IIdentifyable identifyable);
}
