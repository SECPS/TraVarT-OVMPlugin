package de.ovgu.featureide.core.ovm.model;

import java.util.List;

/**
 * TODO description
 *
 * @author johannstoebich
 */
public interface IOvModelVariationPoint extends IOvModelVariationBase {

	/**
	 * This method returns the property alternative.
	 *
	 * @return the property alternative.
	 */
	boolean isAlternative();

	/**
	 * This method sets the property alternative of an OvModelVariationPoint.
	 *
	 * @param alternative the value which will be set.
	 */
	void setAlternative(boolean alternative);

	/**
	 * This method returns true, if there are is a minimum amount of choices defined for this variation point. It is defined if <code>minChoices != -1 </code>.
	 *
	 * @return <code>true</code> if the property min choices is defined.
	 */
	boolean hasMinChoices();

	/**
	 * This method returns the minimum amount of children which have to be chosen at this variation point.
	 *
	 * @return the minimum amount of choices.
	 */
	int getMinChoices();

	/**
	 * This method sets the properties min choices of an OvModelVariationPoint. It determines the minimum children which has to be chosen.
	 *
	 * @param minChoices the value which will be set.
	 */
	void setMinChoices(int minChoices);

	/**
	 * This method returns true, if there is a maximal amount of choices defined for this variation point. It is defined if <code>maxChoices != -1 </code>.
	 *
	 * @return <code>true</code> if the property max choices is defined.
	 */
	boolean hasMaxChoices();

	/**
	 * This method returns the maximum amount of children which can be chosen at this variation point.
	 *
	 * @return the maximum amount of choices.
	 */
	int getMaxChoices();

	/**
	 * This method sets the property max choices of an OvModelVariationPoint. It determines the maximum children which can be chosen.
	 *
	 * @param maxChoices the value which will be set.
	 */
	void setMaxChoices(int maxChoices);

	/**
	 * This method returns true, if there are mandatory children defined for this variation point. It is defined if <code>lenght > 0</code>.
	 *
	 * @return <code>true</code> if there are mandatory children..
	 */
	boolean hasMandatoryChildren();

	/**
	 * This method returns the amount of mandatory children defined for this variation point.
	 *
	 * @return the amount of optional children.
	 */
	int getMandatoryChildrenCount();

	/**
	 * This method returns all mandatory children defined in a variation point.
	 *
	 * @return the amount of mandatory children.
	 */
	List<IOvModelVariationBase> getMandatoryChildren();

	/**
	 * This method adds an child to the list of mandatory children.
	 *
	 * @param ovChild the child which should be added.
	 */
	boolean addMandatoryChild(IOvModelVariationBase mandatoryChild);

	/**
	 * This method removes a mandatory child from a variation point. Which child should be removed is determined by the equals method.
	 * 
	 * @param mandatoryChild the mandatory child which should be removed.
	 */
	boolean removeMandatoryChild(IOvModelVariationBase mandatoryChild);

	/**
	 * This method overwrites all mandatory children of a variation point. This method clones the children list.
	 *
	 * @param mandatoryChildren the new list of mandatory children.
	 */
	void setMandatoryChildren(List<IOvModelVariationBase> mandatoryChildren);

	/**
	 * This method returns true, if there are optional children defined for this variation point. It is defined if <code>lenght > 0</code>.
	 *
	 * @return <code>true</code> if there are optional children.
	 */
	boolean hasOptionalChildren();

	/**
	 * This method returns the amount of optional children defined for this variation point.
	 *
	 * @return the amount of optional children.
	 */
	int getOptionalChildrenCount();

	/**
	 * This method returns all optional children defined in a variation point.
	 *
	 * @return the amount of mandatory children.
	 */
	List<IOvModelVariationBase> getOptionalChildren();

	/**
	 * This method adds an child to the list of optional children.
	 *
	 * @param optionalChild the child which should be added.
	 */
	boolean addOptionalChild(IOvModelVariationBase optionalChild);

	/**
	 * This method removes an optional child from a variation point. Which child should be removed is determined by the equals method.
	 * 
	 * @param optionalChild the optional child which should be removed.
	 */
	boolean removeOptionalChild(IOvModelVariationBase optionalChild);

	/**
	 * This method overwrites all optional children of a variation point. This method clones the list.
	 *
	 * @param optionalChildren the new list of optional children.
	 */
	void setOptionalChildren(List<IOvModelVariationBase> optionalChildren);

}
