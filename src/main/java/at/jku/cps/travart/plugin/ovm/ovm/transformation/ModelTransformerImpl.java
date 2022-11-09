package at.jku.cps.travart.plugin.ovm.ovm.transformation;

import at.jku.cps.travart.core.common.IModelTransformer;
import at.jku.cps.travart.core.exception.NotSupportedVariabilityTypeException;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModel;
import de.vill.model.FeatureModel;

public class ModelTransformerImpl implements IModelTransformer<IOvModel> {

    @Override
    public FeatureModel transform(final IOvModel ovModel, final String name) throws NotSupportedVariabilityTypeException {
        final OvModelToFeatureModelTransformer ovModelToFeatureModelTransformer = new OvModelToFeatureModelTransformer();
        return ovModelToFeatureModelTransformer.transform(ovModel, name);
    }

    @Override
    public IOvModel transform(final FeatureModel featureModel, final String name) throws NotSupportedVariabilityTypeException {
        final FeatureModelToOvModelTransformer featureModeltoOvModelTransformer = new FeatureModelToOvModelTransformer();
        return featureModeltoOvModelTransformer.transform(featureModel, name);
    }
}
