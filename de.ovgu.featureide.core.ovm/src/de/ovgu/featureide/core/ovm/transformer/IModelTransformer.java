package de.ovgu.featureide.core.ovm.transformer;

import de.ovgu.featureide.core.ovm.transformer.impl.exc.NotSupportedTransformationException;
import de.ovgu.featureide.fm.core.base.IFactory;

/**
 * TODO description
 *
 * @author johannstoebich
 */
public interface IModelTransformer<FROM, TO> {

	TO transform(FROM ovModel, IFactory<TO> factory) throws NotSupportedTransformationException;

}
