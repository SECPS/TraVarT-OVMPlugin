package de.ovgu.featureide.core.ovm.model;

/**
 * This is the base interface for an {@link IOvModelVariant} and for an {@link IOvModelVariationPoint}. It contains the common properties of these two classes.
 *
 * @author johannstoebich
 */
public abstract interface IOvModelVariationBase extends IOvModelElement {

	/**
	 * This method returns the metainformation of a variation base (variation point or variant). It stores all information which is defined for a feature in
	 * FeatureIDE however cannot be represented by a variation base.
	 *
	 * @return the metainformation.
	 */
	IOvModelVariationBaseMetainformation getMetainformation();

	/**
	 *
	 * This method sets the metainformation of a variation base (variation point or variant). It overwrites the currently existing metainformatin. The
	 * metainformation stores all information which is defined for a feature in FeatureIDE however cannot be represented by a variation base.
	 *
	 * @param metaInformation the metainformation which should be overwritten.
	 */
	void setMetaInformation(IOvModelVariationBaseMetainformation metaInformation);

	/**
	 * This method returns the property optional. The property optional is not part of the metainformation because it is defined in the OVM.
	 *
	 * @return the property optional.
	 */
	boolean isOptional();

	/**
	 * This method sets the property optional of an OvModelVariationBase (variation point or variant).The property optional is not part of the metainformation
	 * because it is defined in the OVM.
	 *
	 *
	 * @param optional the value which will be set.
	 */
	void setOptional(boolean optional);

}
