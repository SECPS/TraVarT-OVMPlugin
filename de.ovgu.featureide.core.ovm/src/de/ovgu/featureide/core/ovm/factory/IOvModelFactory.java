package de.ovgu.featureide.core.ovm.factory;

import de.ovgu.featureide.core.ovm.model.IIdentifyable;
import de.ovgu.featureide.core.ovm.model.IOvModel;
import de.ovgu.featureide.core.ovm.model.IOvModelVariant;
import de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint;
import de.ovgu.featureide.core.ovm.model.constraint.IOvModelExcludesConstraint;
import de.ovgu.featureide.core.ovm.model.constraint.IOvModelRequiresConstraint;
import de.ovgu.featureide.fm.core.base.IFactory;

/**
 * TODO description
 *
 * @author johannstoebich
 */
public interface IOvModelFactory extends IFactory<IOvModel> {

	IIdentifyable createIdentifyable(int internalId, String name);

	IOvModelVariant createVariant(IOvModel ovModel, String name);

	IOvModelVariationPoint createVariationPoint(IOvModel ovModel, String name);

	IOvModelRequiresConstraint createRequiresConstraint(IOvModel ovModel);

	IOvModelExcludesConstraint createExcludesConstraint(IOvModel ovModel);
}
