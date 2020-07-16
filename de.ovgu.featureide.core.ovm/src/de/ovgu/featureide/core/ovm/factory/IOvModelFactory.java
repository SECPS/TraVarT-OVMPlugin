package de.ovgu.featureide.core.ovm.factory;

import de.ovgu.featureide.core.ovm.model.IIdentifiable;
import de.ovgu.featureide.core.ovm.model.IOvModel;
import de.ovgu.featureide.core.ovm.model.IOvModelVariant;
import de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint;
import de.ovgu.featureide.core.ovm.model.constraint.IOvModelExcludesConstraint;
import de.ovgu.featureide.core.ovm.model.constraint.IOvModelRequiresConstraint;
import de.ovgu.featureide.fm.core.base.IFactory;

/**
 * The factories for producing a new OV-Models or items of an OV-Model should implement this interface.
 *
 * @author johannstoebich
 */
public interface IOvModelFactory extends IFactory<IOvModel> {

	/**
	 * Create a new identifiable for an OvModel. Each element of an OvModel is uniquely identified with an identfyable.
	 *
	 * @param internalId the internal id of the identifiable.
	 * @param name the name of the identifiable
	 * @return the new created identifiable
	 */
	IIdentifiable createIdentifiable(int internalId, String name);

	/**
	 * Creates a new variant.
	 *
	 * @param ovModel the model for which the variant should be created.
	 * @param name the name of the new variant.
	 * @return the newly created variant.
	 */
	IOvModelVariant createVariant(IOvModel ovModel, String name);

	/**
	 * Creates a new variation point.
	 *
	 * @param ovModel the model for which the variation point should be created.
	 * @param name the name of the variation point.
	 * @return the newly created variation point.
	 */
	IOvModelVariationPoint createVariationPoint(IOvModel ovModel, String name);

	/**
	 * This method creates a required constraint.
	 *
	 * @param ovModel the OV-Model for which the requires constraint is created.
	 * @return the newly created requires constraint.
	 */
	IOvModelRequiresConstraint createRequiresConstraint(IOvModel ovModel);

	/**
	 * The method creates an excludes constraint.
	 *
	 * @param ovModel the OV-Model for which the excludes constraint is created.
	 * @return the newly created excludes constraint.
	 */
	IOvModelExcludesConstraint createExcludesConstraint(IOvModel ovModel);
}
