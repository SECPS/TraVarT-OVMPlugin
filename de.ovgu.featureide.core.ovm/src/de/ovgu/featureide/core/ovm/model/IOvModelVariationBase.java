package de.ovgu.featureide.core.ovm.model;

/**
 * TODO description
 *
 * @author johannstoebich
 */
public interface IOvModelVariationBase extends IOvModelElement {

	IOvModelVariationBaseMetainformation getMetainformation();

	void setMetaInformation(IOvModelVariationBaseMetainformation metaInformation);

	boolean isOptional();

	void setOptional(boolean optional);

}
