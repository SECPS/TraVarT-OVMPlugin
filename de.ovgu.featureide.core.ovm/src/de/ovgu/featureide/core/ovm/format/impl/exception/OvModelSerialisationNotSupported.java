package de.ovgu.featureide.core.ovm.format.impl.exception;

/**
 * TODO description
 *
 * @author johannstoebich
 */
public class OvModelSerialisationNotSupported extends OvModelSerialisationException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public OvModelSerialisationNotSupported(Class<?> class1) {
		super("The serialisation of " + class1.getName() + " is not supported.");
	}

}
