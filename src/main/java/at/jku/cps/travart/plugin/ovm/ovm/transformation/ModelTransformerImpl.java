package at.jku.cps.travart.plugin.ovm.ovm.transformation;

import at.jku.cps.travart.core.common.IModelTransformer;
import at.jku.cps.travart.core.exception.NotSupportedVariabilityTypeException;
import at.jku.cps.travart.plugin.ovm.ovm.model.IOvModel;
import de.vill.model.FeatureModel;

public class ModelTransformerImpl implements IModelTransformer<IOvModel> {

    @Override
    public FeatureModel transform(IOvModel ovModel, String name) throws NotSupportedVariabilityTypeException {
        OvModelToFeatureModelTransformer ovModelToFeatureModelTransformer = new OvModelToFeatureModelTransformer();
        ovModelToFeatureModelTransformer.transform(ovModel, name);
    }

    @Override
    public IOvModel transform(FeatureModel featureModel, String name) throws NotSupportedVariabilityTypeException {
        FeatureModeltoOvModelTransformer featureModeltoOvModelTransformer = new FeatureModeltoOvModelTransformer();
        return featureModeltoOvModelTransformer.transform(featureModel, name);
    }
}
