package de.ovgu.featureide.core.ovm.model;

import de.ovgu.featureide.fm.core.base.IPropertyContainer;
import de.ovgu.featureide.fm.core.base.impl.FeatureModel;

/**
 * Manages all additional properties of a OVM. The metainformation stores all information which is defined for a feature model in FeatureIDE however cannot be
 * represented by an OVM. In this case it is solely the description and the properties.
 *
 */
public interface IOvModelMetainformation {

	/**
	 * This method returns the custom properties of the ovModel. The custom properties represent additional properties of an OvModel overtake from the feature
	 * model {@link FeatureModel}. They have been added so that no information is lost during transformation.
	 *
	 * @return the custom properties of the ovModel.
	 */
	IPropertyContainer getCustomProperties();

	/**
	 * This method returns the description of an OvModel.
	 *
	 * @return The description of the OvModel.
	 */
	String getDescription();

	/**
	 * This method sets the description of an OvModel.
	 *
	 * @param description the description which will be set.
	 */
	void setDescription(String description);

}
