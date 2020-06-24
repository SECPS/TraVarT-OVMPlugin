package de.ovgu.featureide.core.ovm.factory.impl;

import de.ovgu.featureide.core.ovm.factory.IOvModelFactory;
import de.ovgu.featureide.core.ovm.model.IIdentifyable;
import de.ovgu.featureide.core.ovm.model.IOvModel;
import de.ovgu.featureide.core.ovm.model.IOvModelVariant;
import de.ovgu.featureide.core.ovm.model.IOvModelVariationPoint;
import de.ovgu.featureide.core.ovm.model.constraint.IOvModelExcludesConstraint;
import de.ovgu.featureide.core.ovm.model.constraint.IOvModelRequiresConstraint;
import de.ovgu.featureide.core.ovm.model.constraint.impl.OvModelExcludesConstraint;
import de.ovgu.featureide.core.ovm.model.constraint.impl.OvModelRequiresConstraint;
import de.ovgu.featureide.core.ovm.model.impl.Identifyable;
import de.ovgu.featureide.core.ovm.model.impl.OvModel;
import de.ovgu.featureide.core.ovm.model.impl.OvModelVariant;
import de.ovgu.featureide.core.ovm.model.impl.OvModelVariationPoint;

/**
 * TODO description
 *
 * @author johannstoebich
 */
public class OvModelFactory implements IOvModelFactory {

	public static final String ID = "de.ovgu.featureide.core.ovm.factory.OvmModelFactory";

	public static OvModelFactory getInstance() {
		return new OvModelFactory();
	}

	public OvModelFactory() {}

	@Override
	public String getId() {
		return ID;
	}

	@Override
	public boolean initExtension() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see de.ovgu.featureide.fm.core.base.IFactory#create()
	 */
	@Override
	public IOvModel create() {
		return new OvModel(ID);
	}

	/*
	 * (non-Javadoc)
	 * @see de.ovgu.featureide.core.ovm.factory.IOvModelFactory#createVariant(de.ovgu.featureide.core.ovm.model.IOvModel, java.lang.String)
	 */
	@Override
	public IOvModelVariant createVariant(IOvModel ovModel, String name) {
		final OvModelVariant ovModelVariant = new OvModelVariant();
		ovModelVariant.setName(name);
		return ovModelVariant;
	}

	/*
	 * (non-Javadoc)
	 * @see de.ovgu.featureide.core.ovm.factory.IOvModelFactory#createVariationPoint(de.ovgu.featureide.core.ovm.model.IOvModel, java.lang.String)
	 */
	@Override
	public IOvModelVariationPoint createVariationPoint(IOvModel ovModel, String name) {
		final OvModelVariationPoint ovModelVariationPoint = new OvModelVariationPoint();
		ovModelVariationPoint.setName(name);
		return ovModelVariationPoint;
	}

	/*
	 * (non-Javadoc)
	 * @see de.ovgu.featureide.core.ovm.factory.IOvModelFactory#createRequiresConstraint(de.ovgu.featureide.core.ovm.model.IOvModel)
	 */
	@Override
	public IOvModelRequiresConstraint createRequiresConstraint(IOvModel ovModel) {
		final OvModelRequiresConstraint ovModelRequiresConstraint = new OvModelRequiresConstraint();
		return ovModelRequiresConstraint;
	}

	/*
	 * (non-Javadoc)
	 * @see de.ovgu.featureide.core.ovm.factory.IOvModelFactory#createExcludesConstraint(de.ovgu.featureide.core.ovm.model.IOvModel)
	 */
	@Override
	public IOvModelExcludesConstraint createExcludesConstraint(IOvModel ovModel) {
		final OvModelExcludesConstraint ovModelExcludesConstraint = new OvModelExcludesConstraint();
		return ovModelExcludesConstraint;
	}

	/*
	 * (non-Javadoc)
	 * @see de.ovgu.featureide.core.ovm.factory.IOvModelFactory#createIdent(int, java.lang.String)
	 */
	@Override
	public IIdentifyable createIdentifyable(int internalIdGiven, String nameGiven) {
		return new Identifyable() {

			{
				setInternalId(internalIdGiven);
				setName(nameGiven);
			}
		};
	}

}
