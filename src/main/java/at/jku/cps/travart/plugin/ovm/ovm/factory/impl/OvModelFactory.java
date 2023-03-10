package at.jku.cps.travart.plugin.ovm.ovm.factory.impl;

import at.jku.cps.travart.plugin.ovm.ovm.factory.IOvModelFactory;
import at.jku.cps.travart.plugin.ovm.ovm.model.IIdentifiable;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModel;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariant;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModelVariationPoint;
import at.jku.cps.travart.plugin.ovm.ovm.model.constraint.IOvModelExcludesConstraint;
import at.jku.cps.travart.plugin.ovm.ovm.model.constraint.IOvModelRequiresConstraint;
import at.jku.cps.travart.plugin.ovm.ovm.model.constraint.impl.OvModelExcludesConstraint;
import at.jku.cps.travart.plugin.ovm.ovm.model.constraint.impl.OvModelRequiresConstraint;
import at.jku.cps.travart.plugin.ovm.ovm.model.impl.Identifiable;
import at.jku.cps.travart.plugin.ovm.ovm.model.impl.OvModel;
import at.jku.cps.travart.plugin.ovm.ovm.model.impl.OvModelVariant;
import at.jku.cps.travart.plugin.ovm.ovm.model.impl.OvModelVariationPoint;

/**
 * This repents a concrete implementation of the {@link IOvModelFactory}.
 *
 * @author johannstoebich
 */
public class OvModelFactory implements IOvModelFactory {

    public static final String ID = "at.jku.cps.travart.ovm.factory.OvmModelFactory";

    public OvModelFactory() {
    }

    public static OvModelFactory getInstance() {
        return new OvModelFactory();
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public boolean initExtension() {
        return true;
    }

    /**
     * (non-Javadoc)
     */
    @Override
    public IOvModel create() {
        return new OvModel(ID);
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.factory.IOvModelFactory#createVariant(at.jku.cps.travart.plugin.ovm.ovm.model.IOvModel,
     * String)
     */
    @Override
    public IOvModelVariant createVariant(final IOvModel ovModel, final String name) {
        final OvModelVariant ovModelVariant = new OvModelVariant();
        ovModelVariant.setName(name);
        return ovModelVariant;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.factory.IOvModelFactory#createVariationPoint(at.jku.cps.travart.plugin.ovm.ovm.model.IOvModel,
     * String)
     */
    @Override
    public IOvModelVariationPoint createVariationPoint(final IOvModel ovModel, final String name) {
        final OvModelVariationPoint ovModelVariationPoint = new OvModelVariationPoint();
        ovModelVariationPoint.setName(name);
        return ovModelVariationPoint;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.factory.IOvModelFactory#createRequiresConstraint(at.jku.cps.travart.plugin.ovm.ovm.model.IOvModel)
     */
    @Override
    public IOvModelRequiresConstraint createRequiresConstraint(final IOvModel ovModel) {
        final OvModelRequiresConstraint ovModelRequiresConstraint = new OvModelRequiresConstraint();
        return ovModelRequiresConstraint;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.factory.IOvModelFactory#createExcludesConstraint(at.jku.cps.travart.plugin.ovm.ovm.model.IOvModel)
     */
    @Override
    public IOvModelExcludesConstraint createExcludesConstraint(final IOvModel ovModel) {
        final OvModelExcludesConstraint ovModelExcludesConstraint = new OvModelExcludesConstraint();
        return ovModelExcludesConstraint;
    }

    /**
     * (non-Javadoc)
     *
     * @see at.jku.cps.travart.plugin.ovm.ovm.factory.IOvModelFactory#createIdentifiable(int,
     * String)
     */
    @Override
    public IIdentifiable createIdentifiable(final int internalIdGiven, final String nameGiven) {
        return new Identifiable() {

            {
                this.setInternalId(internalIdGiven);
                this.setName(nameGiven);
            }
        };
    }

}
