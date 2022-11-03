import de.ovgu.featureide.core.ovm.factory.IOvModelFactory;

/**
 * This repents a concrete implementation of the {@link IOvModelFactory}.
 *
 * @author johannstoebich
 */
public class OvModelFactory implements IOvModelFactory {

    public static final String ID = "de.ovgu.featureide.core.ovm.factory.OvmModelFactory";

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
     *
     * @see de.ovgu.featureide.fm.core.base.IFactory#create()
     */
    @Override
    public IOvModel create() {
        return new OvModel(ID);
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.factory.IOvModelFactory#createVariant(IOvModel, java.lang.String)
     */
    @Override
    public IOvModelVariant createVariant(IOvModel ovModel, String name) {
        OvModelVariant ovModelVariant = new OvModelVariant();
        ovModelVariant.setName(name);
        return ovModelVariant;
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.factory.IOvModelFactory#createVariationPoint(IOvModel, java.lang.String)
     */
    @Override
    public IOvModelVariationPoint createVariationPoint(IOvModel ovModel, String name) {
        OvModelVariationPoint ovModelVariationPoint = new OvModelVariationPoint();
        ovModelVariationPoint.setName(name);
        return ovModelVariationPoint;
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.factory.IOvModelFactory#createRequiresConstraint(IOvModel)
     */
    @Override
    public IOvModelRequiresConstraint createRequiresConstraint(IOvModel ovModel) {
        OvModelRequiresConstraint ovModelRequiresConstraint = new OvModelRequiresConstraint();
        return ovModelRequiresConstraint;
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.factory.IOvModelFactory#createExcludesConstraint(IOvModel)
     */
    @Override
    public IOvModelExcludesConstraint createExcludesConstraint(IOvModel ovModel) {
        OvModelExcludesConstraint ovModelExcludesConstraint = new OvModelExcludesConstraint();
        return ovModelExcludesConstraint;
    }

    /**
     * (non-Javadoc)
     *
     * @see de.ovgu.featureide.core.ovm.factory.IOvModelFactory#createIdentifiable(int, java.lang.String)
     */
    @Override
    public IIdentifiable createIdentifiable(int internalIdGiven, String nameGiven) {
        return new Identifiable() {

            {
                this.setInternalId(internalIdGiven);
                this.setName(nameGiven);
            }
        };
    }

}
