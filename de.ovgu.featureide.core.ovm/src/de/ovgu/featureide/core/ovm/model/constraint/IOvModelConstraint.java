package de.ovgu.featureide.core.ovm.model.constraint;

import de.ovgu.featureide.core.ovm.model.IOvModelElement;
import de.ovgu.featureide.core.ovm.model.IOvModelVariationBase;

/**
 * TODO description
 *
 * @author johannstoebich
 */
public abstract interface IOvModelConstraint extends IOvModelElement {

	IOvModelConstraintMetainformation getMetainformation();

	IOvModelVariationBase getSource();

	void setSource(IOvModelVariationBase source);

	IOvModelVariationBase getTarget();

	void setTarget(IOvModelVariationBase target);
}
