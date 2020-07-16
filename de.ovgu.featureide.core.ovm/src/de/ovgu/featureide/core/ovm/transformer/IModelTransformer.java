package de.ovgu.featureide.core.ovm.transformer;

import de.ovgu.featureide.core.ovm.transformer.impl.exc.NotSupportedTransformationException;
import de.ovgu.featureide.fm.core.base.IFactory;

/**
 * This is the interface for a transformer. It has a transform method which takes an source object and will return an target object which it has been created
 * during the transformation process. The new object will be created by the given factory.
 *
 * @author johannstoebich
 */
public interface IModelTransformer<FROM, TO> {

	/**
	 * This method transforms an given object to an new object. The new object will be created by the given factory.
	 * 
	 * @param from The source object.
	 * @param factory The factory which creates the target object.
	 * @return the new target object.
	 * @throws NotSupportedTransformationException
	 */
	TO transform(FROM from, IFactory<TO> factory) throws NotSupportedTransformationException;

}
