package de.ovgu.featureide.core.ovm.model;

import java.util.List;

/**
 * TODO description
 *
 * @author johannstoebich
 */
public interface IOvModelVariationPoint extends IOvModelVariationBase {

	boolean isAlternative();

	void setAlternative(boolean alternative);

	boolean hasMinChoices();

	/**
	 * The min choices defined by the alternative
	 * 
	 * @return
	 */
	int getMinChoices();

	void setMinChoices(int minChoices);

	boolean hasMaxChoices();

	int getMaxChoices();

	void setMaxChoices(int maxChoices);

	boolean hasMandatoryChildren();

	int getMandatoryChildrenCount();

	List<IOvModelVariationBase> getMandatoryChildren();

	boolean addMandatoryChild(IOvModelVariationBase mandatoryChild);

	boolean removeMandatoryChild(IOvModelVariationBase mandatoryChild);

	void setMandatoryChildren(List<IOvModelVariationBase> mandatoryChildren);

	boolean hasOptionalChildren();

	int getOptionalChildrenCount();

	List<IOvModelVariationBase> getOptionalChildren();

	boolean addOptionalChild(IOvModelVariationBase optionalChild);

	boolean removeOptionalChild(IOvModelVariationBase optionalChild);

	void setOptionalChildren(List<IOvModelVariationBase> optionalChildren);

}
