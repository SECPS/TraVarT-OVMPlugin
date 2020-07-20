package de.ovgu.featureide.core.ovm.model.constraint;

import de.ovgu.featureide.core.ovm.model.IOvModelElement;
import de.ovgu.featureide.core.ovm.model.IOvModelVariationBase;

/**
 * TODO description
 *
 * @author johannstoebich
 */
public abstract interface IOvModelConstraint extends IOvModelElement {

	/**
	 * This method returns the metainformation of a constraint (variation point or variant). It stores all information which is defined for a constraint in
	 * FeatureIDE however cannot be represented by an OVM.
	 *
	 * @return the metainformation.
	 */
	IOvModelConstraintMetainformation getMetainformation();

	/**
	 * This method returns the well defined source of a constraint as variation base (variation point or variant).
	 *
	 * @return the source of the variation base (variation point or variant).
	 */
	IOvModelVariationBase getSource();

	/**
	 * This method sets the source of an OvModel constraint. An OvModel constrain has always a well defined source.
	 *
	 * @param source the source as variation base (variation point or variant) of the constraint.
	 */
	void setSource(IOvModelVariationBase source);

	/**
	 * This method returns the well defined target of a constraint as variation base (variation point or variant).
	 *
	 * @return the target of the constraint as variation base (variation point or variant).
	 */
	IOvModelVariationBase getTarget();

	/**
	 * This method sets the target of an OvModel constraint. An OvModel constrain has always a well defined target.
	 *
	 * @param target the target variation base (variation point or variant) of the constraint.
	 */
	void setTarget(IOvModelVariationBase target);
}
